package ru.flowernetes.orchestration.domain.usecase

import io.fabric8.kubernetes.api.model.Quantity
import io.fabric8.kubernetes.api.model.batch.JobBuilder
import io.fabric8.kubernetes.client.KubernetesClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import ru.flowernetes.containerization.api.domain.usecase.GetTaskImageOrCreateUseCase
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.task.TaskStatus
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.orchestration.CPU_RESOURCE
import ru.flowernetes.orchestration.MEMORY_RESOURCE
import ru.flowernetes.orchestration.api.domain.entity.JobLabelKeys
import ru.flowernetes.orchestration.api.domain.usecase.RunTaskUseCase
import ru.flowernetes.orchestration.data.provider.TaskJobNameProvider
import ru.flowernetes.task.api.domain.usecase.CheckTaskNotExceedResourceQuotaUseCase
import ru.flowernetes.workload.api.domain.model.WorkloadModel
import ru.flowernetes.workload.api.domain.usecase.AddWorkloadUseCase
import ru.flowernetes.workload.api.domain.usecase.UpdateWorkloadUseCase
import java.time.LocalDateTime

@Component
open class RunTaskUseCaseImpl(
  private val getTaskImageOrCreateUseCase: GetTaskImageOrCreateUseCase,
  private val taskJobNameProvider: TaskJobNameProvider,
  private val kubernetesClient: KubernetesClient,
  private val addWorkloadUseCase: AddWorkloadUseCase,
  private val checkTaskNotExceedResourceQuotaUseCase: CheckTaskNotExceedResourceQuotaUseCase,
  private val updateWorkloadUseCase: UpdateWorkloadUseCase,
  @Value("\${kubernetes.api.version}")
  private val kubernetesApiVersion: String
) : RunTaskUseCase {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Async
    override fun execAsync(task: Task) {
        log.info("Running $task")

        val jobName = taskJobNameProvider.get(task)
        val namespace = task.workflow.team.namespace
        val workload = addNewWorkload(task)

        kotlin.runCatching {
            checkTaskNotExceedResourceQuotaUseCase.exec(task)
        }.onFailure {
            logError(it)
            updateWorkloadOnError(workload, TaskStatus.QUOTA_EXCEEDED)
            return@execAsync
        }
        kotlin.runCatching {
            checkJobNameNotExists(namespace, jobName)
        }.onFailure {
            logError(it)
            updateWorkloadOnError(workload, TaskStatus.KILLED)
            return@execAsync
        }

        val image = getTaskImageOrCreateUseCase.exec(task)

        val job = JobBuilder()
          .withApiVersion("batch/$kubernetesApiVersion")
          .withNewMetadata()
            .withName(jobName)
            .withLabels(mapOf(
              Pair(JobLabelKeys.WORKLOAD_ID.name, workload.id.toString())))
          .endMetadata()
          .withNewSpec()
            .withActiveDeadlineSeconds(task.timeDeadline)
            .withBackoffLimit(task.maxRetries)
            .withNewTemplate()
              .withNewSpec()
                .withRestartPolicy("Never")
                .addNewContainer()
                  .withName(jobName)
                  .withImage(image)
                  .withNewResources()
                    .withRequests(mapOf(
                      Pair(CPU_RESOURCE, Quantity(task.cpuRequest.toString())),
                      Pair(MEMORY_RESOURCE, Quantity(task.memoryRequest.toString()))))
                    .withLimits(mapOf(
                      Pair(CPU_RESOURCE, Quantity(task.cpuLimit.toString())),
                      Pair(MEMORY_RESOURCE, Quantity(task.memoryLimit.toString()))))
                  .endResources()
                .endContainer()
              .endSpec()
            .endTemplate()
          .endSpec()
          .build()

        log.info("Kubernetes job built for $task : $job")

        kubernetesClient.batch().jobs().inNamespace(namespace).create(job)

        log.info("Started $task")
    }

    private fun checkJobNameNotExists(namespace: String, jobName: String) {
        kubernetesClient.batch().jobs().inNamespace(namespace).withName(jobName).get()?.let {
            throw IllegalStateException("Job with name $jobName already exists")
        }
    }

    private fun logError(throwable: Throwable) {
        log.error("Run task failed: ${throwable.message}", throwable)
    }

    private fun updateWorkloadOnError(workload: Workload, taskStatus: TaskStatus): Workload {
        return updateWorkloadUseCase.exec(workload.copy(
          taskStartTime = LocalDateTime.now(),
          taskCompletionTime = LocalDateTime.now(),
          lastTransitionTime = System.currentTimeMillis(),
          taskStatus = taskStatus
        ))
    }

    private fun addNewWorkload(task: Task): Workload {
        return addWorkloadUseCase.exec(WorkloadModel(
          task = task,
          taskStatus = TaskStatus.PENDING,
          baseImage = task.baseImage,
          memoryRequest = task.memoryRequest,
          memoryLimit = task.memoryLimit,
          cpuRequest = task.cpuRequest,
          cpuLimit = task.cpuLimit,
          sourceScriptId = task.sourceScriptId
        ))
    }
}