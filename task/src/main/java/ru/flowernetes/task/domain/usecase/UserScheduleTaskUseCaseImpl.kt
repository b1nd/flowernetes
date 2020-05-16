package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.task.TaskStatus
import ru.flowernetes.monitoring.api.domain.usecase.GetTaskStatusInfoUseCase
import ru.flowernetes.monitoring.api.domain.usecase.SendWorkflowTaskStatusMessageUseCase
import ru.flowernetes.scheduling.api.domain.usecase.ScheduleTaskUseCase
import ru.flowernetes.task.api.domain.usecase.CheckUserTaskControlAllowedUseCase
import ru.flowernetes.task.api.domain.usecase.UserScheduleTaskUseCase
import ru.flowernetes.task.data.repo.TaskRepository

@Component
class UserScheduleTaskUseCaseImpl(
  private val taskRepository: TaskRepository,
  private val checkUserTaskControlAllowedUseCase: CheckUserTaskControlAllowedUseCase,
  private val getTaskStatusInfoUseCase: GetTaskStatusInfoUseCase,
  private val sendWorkflowTaskStatusMessageUseCase: SendWorkflowTaskStatusMessageUseCase,
  private val scheduleTaskUseCase: ScheduleTaskUseCase
) : UserScheduleTaskUseCase {

    override fun exec(task: Task) {
        checkUserTaskControlAllowedUseCase.exec(task)
        taskRepository.save(task.copy(scheduled = true)).also {
            scheduleTaskUseCase.exec(it)
        }
        val taskStatusInfo = getTaskStatusInfoUseCase.exec(task).copy(
          taskStatus = TaskStatus.WAITING
        )
        sendWorkflowTaskStatusMessageUseCase.exec(task.workflow, taskStatusInfo)
    }
}