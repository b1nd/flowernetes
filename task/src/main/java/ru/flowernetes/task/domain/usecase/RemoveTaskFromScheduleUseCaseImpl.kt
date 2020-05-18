package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.monitoring.api.domain.usecase.GetTaskStatusInfoUseCase
import ru.flowernetes.monitoring.api.domain.usecase.SendWorkflowTaskStatusMessageUseCase
import ru.flowernetes.scheduling.api.domain.usecase.RemoveTaskScheduleUseCase
import ru.flowernetes.task.api.domain.usecase.RemoveTaskFromScheduleUseCase
import ru.flowernetes.task.api.domain.usecase.UnmarkTaskDependencyMarkersUseCase
import ru.flowernetes.task.data.repo.TaskRepository

@Component
class RemoveTaskFromScheduleUseCaseImpl(
  private val removeTaskScheduleUseCase: RemoveTaskScheduleUseCase,
  private val getTaskStatusInfoUseCase: GetTaskStatusInfoUseCase,
  private val unmarkTaskDependencyMarkersUseCase: UnmarkTaskDependencyMarkersUseCase,
  private val sendWorkflowTaskStatusMessageUseCase: SendWorkflowTaskStatusMessageUseCase,
  private val taskRepository: TaskRepository
) : RemoveTaskFromScheduleUseCase {

    override fun exec(task: Task) {
        taskRepository.save(task.copy(scheduled = false)).also {
            removeTaskScheduleUseCase.exec(task)
        }
        unmarkTaskDependencyMarkersUseCase.exec(task)
        val taskStatusInfo = getTaskStatusInfoUseCase.exec(task)
        sendWorkflowTaskStatusMessageUseCase.exec(task.workflow, taskStatusInfo)
    }
}