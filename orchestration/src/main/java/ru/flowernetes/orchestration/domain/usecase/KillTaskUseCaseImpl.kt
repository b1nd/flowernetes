package ru.flowernetes.orchestration.domain.usecase

import io.fabric8.kubernetes.client.KubernetesClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.task.TaskStatus
import ru.flowernetes.orchestration.api.domain.entity.JobLabelKeys
import ru.flowernetes.orchestration.api.domain.usecase.KillTaskUseCase
import ru.flowernetes.orchestration.data.provider.TaskJobNameProvider
import ru.flowernetes.orchestration.getLabel
import ru.flowernetes.workload.api.domain.usecase.GetWorkloadByIdUseCase
import ru.flowernetes.workload.api.domain.usecase.UpdateWorkloadUseCase
import java.time.LocalDateTime

@Component
class KillTaskUseCaseImpl(
  private val taskJobNameProvider: TaskJobNameProvider,
  private val getWorkloadByIdUseCase: GetWorkloadByIdUseCase,
  private val updateWorkloadUseCase: UpdateWorkloadUseCase,
  private val kubernetesClient: KubernetesClient
) : KillTaskUseCase {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun exec(task: Task) {
        log.info("Killing $task")

        val jobName = taskJobNameProvider.get(task)
        val namespace = task.workflow.team.namespace

        val job = kubernetesClient.batch().jobs()
          .inNamespace(namespace)
          .withName(jobName)
          .get() ?: throw IllegalStateException("Task ${task.name} cannot be killed: Task is not running")

        kubernetesClient.batch().jobs().delete(job)

        val workloadId = job.getLabel(JobLabelKeys.WORKLOAD_ID).toLong()
        val workload = getWorkloadByIdUseCase.exec(workloadId)

        updateWorkloadUseCase.exec(
          workload.copy(
            taskStartTime = workload.taskStartTime ?: LocalDateTime.now(),
            taskCompletionTime = LocalDateTime.now(),
            lastTransitionTime = System.currentTimeMillis(),
            taskStatus = TaskStatus.KILLED
          )
        )
    }
}