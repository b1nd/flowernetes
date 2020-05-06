package ru.flowernetes.monitoring.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.monitoring.TaskStatusInfo
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.task.TaskStatus
import ru.flowernetes.monitoring.api.domain.usecase.GetTaskStatusInfoFromWorkloadUseCase
import ru.flowernetes.monitoring.api.domain.usecase.GetTaskStatusInfoUseCase
import ru.flowernetes.workload.api.domain.usecase.GetTaskLastWorkloadUseCase

@Component
class GetTaskStatusInfoUseCaseImpl(
  private val getTaskLastWorkloadUseCase: GetTaskLastWorkloadUseCase,
  private val getTaskStatusInfoFromWorkloadUseCase: GetTaskStatusInfoFromWorkloadUseCase
) : GetTaskStatusInfoUseCase {

    override fun exec(task: Task): TaskStatusInfo {
        val lastWorkload = getTaskLastWorkloadUseCase.exec(task)
          ?: return if (task.scheduled) {
              task.toTaskStatusInfo(TaskStatus.WAITING)
          } else {
              task.toTaskStatusInfo(TaskStatus.INACTIVE)
          }
        return getTaskStatusInfoFromWorkloadUseCase.exec(lastWorkload)
    }

    private fun Task.toTaskStatusInfo(taskStatus: TaskStatus): TaskStatusInfo {
        return TaskStatusInfo(
          id,
          taskStatus = taskStatus,
          lastTransitionTime = System.currentTimeMillis()
        )
    }
}