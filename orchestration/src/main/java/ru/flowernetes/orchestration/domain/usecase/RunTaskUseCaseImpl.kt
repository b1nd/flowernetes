package ru.flowernetes.orchestration.domain.usecase

import io.fabric8.kubernetes.api.model.Quantity
import io.fabric8.kubernetes.api.model.batch.JobBuilder
import io.fabric8.kubernetes.client.KubernetesClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import ru.flowernetes.containerization.api.domain.usecase.GetTaskImageOrCreateUseCase
import ru.flowernetes.entity.task.Task
import ru.flowernetes.orchestration.CPU_RESOURCE
import ru.flowernetes.orchestration.MEMORY_RESOURCE
import ru.flowernetes.orchestration.api.domain.usecase.RunTaskUseCase
import ru.flowernetes.orchestration.data.provider.TaskJobNameProvider

@Component
class RunTaskUseCaseImpl(
  private val getTaskImageOrCreateUseCase: GetTaskImageOrCreateUseCase,
  private val taskJobNameProvider: TaskJobNameProvider,
  private val kubernetesClient: KubernetesClient,
  @Value("\${kubernetes.api.version}")
  private val kubernetesApiVersion: String
) : RunTaskUseCase {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun exec(task: Task) {
        log.info("Running $task")

        val image = getTaskImageOrCreateUseCase.exec(task)
        val namespace = task.workflow.team.namespace
        val jobName = taskJobNameProvider.get(task)

        checkJobNameNotExists(namespace, jobName)

        val job = JobBuilder()
          .withApiVersion("batch/$kubernetesApiVersion")
          .withNewMetadata()
            .withName(jobName)
          .endMetadata()
          .withNewSpec()
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
}