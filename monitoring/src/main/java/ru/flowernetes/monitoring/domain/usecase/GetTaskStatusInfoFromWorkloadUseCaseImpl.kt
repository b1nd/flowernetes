package ru.flowernetes.monitoring.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.monitoring.TaskStatusInfo
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.monitoring.api.domain.usecase.GetTaskStatusFromWorkloadUseCase
import ru.flowernetes.monitoring.api.domain.usecase.GetTaskStatusInfoFromWorkloadUseCase

@Component
class GetTaskStatusInfoFromWorkloadUseCaseImpl(
  private val getTaskStatusFromWorkloadUseCase: GetTaskStatusFromWorkloadUseCase
) : GetTaskStatusInfoFromWorkloadUseCase {

    override fun exec(workload: Workload): TaskStatusInfo {
        val taskStatus = getTaskStatusFromWorkloadUseCase.exec(workload)
        return TaskStatusInfo(
          taskId = workload.task.id,
          taskStatus = taskStatus,
          lastTransitionTime = System.currentTimeMillis()
        )
    }
}