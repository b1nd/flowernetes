package ru.flowernetes.orchestration.data.service

import io.fabric8.kubernetes.api.model.batch.Job
import io.fabric8.kubernetes.client.KubernetesClient
import io.fabric8.kubernetes.client.Watcher
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import ru.flowernetes.entity.task.TaskStatus
import ru.flowernetes.orchestration.JOB_NAME_LABEL
import ru.flowernetes.orchestration.JobConditionReason
import ru.flowernetes.orchestration.api.domain.entity.JobLabelKeys
import ru.flowernetes.orchestration.api.domain.usecase.SaveLogAndDataFromLogReaderUseCase
import ru.flowernetes.orchestration.api.domain.usecase.SaveLogFromLogReaderUseCase
import ru.flowernetes.orchestration.checkJobHasRequiredLabels
import ru.flowernetes.orchestration.data.dto.JobStatus
import ru.flowernetes.orchestration.data.dto.JobStatusType
import ru.flowernetes.orchestration.data.parser.JobTimeParser
import ru.flowernetes.orchestration.getLabel
import ru.flowernetes.workload.api.domain.usecase.GetWorkloadByIdUseCase
import ru.flowernetes.workload.api.domain.usecase.UpdateWorkloadUseCase
import java.io.Reader

@Service
open class JobReceiverService(
  private val kubernetesClient: KubernetesClient,
  private val updateWorkloadUseCase: UpdateWorkloadUseCase,
  private val getWorkloadByIdUseCase: GetWorkloadByIdUseCase,
  private val saveLogAndDataFromLogReaderUseCase: SaveLogAndDataFromLogReaderUseCase,
  private val saveLogFromLogReaderUseCase: SaveLogFromLogReaderUseCase,
  private val jobTimeParser: JobTimeParser
) {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Async
    open fun receive(action: Watcher.Action, job: Job) {
        log.debug("Received $action $job")

        if (!checkJobHasRequiredLabels(job)) {
            log.debug("Skipping job $job")
            return
        }

        val workloadId = job.getLabel(JobLabelKeys.WORKLOAD_ID).toLong()
        val workload = kotlin.runCatching {
            getWorkloadByIdUseCase.exec(workloadId)
        }.getOrElse {
            log.error(it.message, it)
            job.delete()
            return
        }

        when (action) {
            Watcher.Action.ADDED, Watcher.Action.MODIFIED -> {
                when (job.status.active) {
                    1 -> {
                        updateWorkloadUseCase.exec(workload.copy(
                          taskStartTime = job.status.startTime?.let(jobTimeParser::parse),
                          taskStatus = TaskStatus.RUNNING
                        ))
                    }
                    null -> {
                        job.status.conditions.forEach {
                            if (it.type == JobStatusType.Complete.name && it.status == JobStatus.True.name) {
                                kotlin.runCatching {
                                    saveLogAndDataFromLogReaderUseCase.exec(workload, job.reader())
                                }.onFailure { exception ->
                                    log.error(exception.message, exception)
                                    // todo: update workload doesn't have log and data
                                }
                                updateWorkloadUseCase.exec(workload.copy(
                                  taskStartTime = job.status.startTime?.let(jobTimeParser::parse),
                                  taskCompletionTime = job.status.completionTime?.let(jobTimeParser::parse),
                                  lastTransitionTime = System.currentTimeMillis(),
                                  taskStatus = TaskStatus.SUCCESS
                                ))
                                job.delete()
                                return
                            }
                            if (it.type == JobStatusType.Failed.name && it.status == JobStatus.True.name) {
                                when (it.reason) {
                                    JobConditionReason.DeadlineExceeded.name -> {
                                        // todo: update workload doesn't have log
                                        updateWorkloadUseCase.exec(workload.copy(
                                          taskStartTime = job.status.startTime?.let(jobTimeParser::parse),
                                          taskCompletionTime = it.lastTransitionTime?.let(jobTimeParser::parse),
                                          lastTransitionTime = System.currentTimeMillis(),
                                          taskStatus = TaskStatus.TIME_EXCEEDED
                                        ))
                                    }
                                    else -> {
                                        kotlin.runCatching {
                                            saveLogFromLogReaderUseCase.exec(workload, job.reader())
                                        }.onFailure { exception ->
                                            log.error(exception.message, exception)
                                            // todo: update workload doesn't have log
                                        }
                                        updateWorkloadUseCase.exec(workload.copy(
                                          taskStartTime = job.status.startTime?.let(jobTimeParser::parse),
                                          taskCompletionTime = it.lastTransitionTime?.let(jobTimeParser::parse),
                                          lastTransitionTime = System.currentTimeMillis(),
                                          taskStatus = TaskStatus.ERROR
                                        ))
                                    }
                                }
                                job.delete()
                                return
                            }
                        }
                    }
                }
            }
            Watcher.Action.DELETED -> {
                // todo: if job deleted outside system
            }
            Watcher.Action.ERROR -> {
                // todo: idk
            }
        }
    }

    private fun Job.reader(): Reader {
        val pods = kubernetesClient.pods()
          .inNamespace(metadata.namespace)
          .withLabel(JOB_NAME_LABEL, metadata.name)
          .list()
          .items
        val lastPod = pods.maxBy { pod ->
            pod.status.conditions
              .mapNotNull { it.lastTransitionTime?.let(jobTimeParser::parse) }
              .max() ?: throw IllegalStateException("Pod ${pod.metadata.name} has no conditions")
        } ?: throw IllegalStateException("Job ${metadata.name} has no pods")
        return kubernetesClient.pods()
          .inNamespace(metadata.namespace)
          .withName(lastPod.metadata.name)
          .logReader
    }

    private fun Job.delete(): Boolean {
        return kubernetesClient.batch().jobs().delete(this)
    }
}