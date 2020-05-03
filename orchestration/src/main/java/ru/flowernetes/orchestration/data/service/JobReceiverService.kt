package ru.flowernetes.orchestration.data.service

import io.fabric8.kubernetes.api.model.batch.Job
import io.fabric8.kubernetes.client.KubernetesClient
import io.fabric8.kubernetes.client.Watcher
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import ru.flowernetes.entity.task.TaskStatus
import ru.flowernetes.orchestration.api.domain.entity.JobLabelKeys
import ru.flowernetes.orchestration.api.domain.entity.NoSuchJobLabelException
import ru.flowernetes.orchestration.api.domain.usecase.SaveLogAndDataFromLogReaderUseCase
import ru.flowernetes.orchestration.api.domain.usecase.SaveLogFromLogReaderUseCase
import ru.flowernetes.orchestration.data.dto.JobStatus
import ru.flowernetes.orchestration.data.dto.JobStatusType
import ru.flowernetes.orchestration.data.parser.JobTimeParser
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
            deleteJob(job)
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
                                saveLogAndDataFromLogReaderUseCase.exec(workload, job.reader())

                                updateWorkloadUseCase.exec(workload.copy(
                                  taskStartTime = job.status.startTime?.let(jobTimeParser::parse),
                                  taskCompletionTime = job.status.completionTime?.let(jobTimeParser::parse),
                                  jobName = null,
                                  taskStatus = TaskStatus.SUCCESS
                                ))
                                deleteJob(job)
                                return
                            }
                            if (it.type == JobStatusType.Failed.name && it.status == JobStatus.True.name) {
                                saveLogFromLogReaderUseCase.exec(workload, job.reader())

                                updateWorkloadUseCase.exec(workload.copy(
                                  taskStartTime = job.status.startTime?.let(jobTimeParser::parse),
                                  taskCompletionTime = it.lastTransitionTime?.let(jobTimeParser::parse),
                                  jobName = null,
                                  taskStatus = TaskStatus.ERROR
                                ))
                                deleteJob(job)
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

    private fun deleteJob(job: Job): Boolean {
        return kubernetesClient.batch().jobs().delete(job)
    }

    private fun Job.getLabel(jobLabelKey: JobLabelKeys): String {
        return metadata.labels[jobLabelKey.name] ?: throw NoSuchJobLabelException(toString(), jobLabelKey)
    }

    private fun Job.reader(): Reader {
        return kubernetesClient.batch().jobs()
          .inNamespace(metadata.namespace)
          .withName(metadata.name)
          .logReader
    }

    private fun checkJobHasRequiredLabels(job: Job): Boolean {
        val labels = job.metadata.labels
        return labels.containsKey(JobLabelKeys.WORKLOAD_ID.name)
    }
}