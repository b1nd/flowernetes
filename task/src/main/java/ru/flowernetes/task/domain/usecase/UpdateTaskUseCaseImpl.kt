package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.monitoring.api.domain.usecase.GetTaskStatusInfoUseCase
import ru.flowernetes.monitoring.api.domain.usecase.SendWorkflowTaskStatusMessageUseCase
import ru.flowernetes.scheduling.api.domain.usecase.ScheduleTaskUseCase
import ru.flowernetes.task.api.domain.dto.TaskDto
import ru.flowernetes.task.api.domain.usecase.*
import ru.flowernetes.task.data.mapper.TaskDtoMapper
import ru.flowernetes.task.data.repo.TaskRepository

@Component
class UpdateTaskUseCaseImpl(
  private val taskRepository: TaskRepository,
  private val validateTaskUseCase: ValidateTaskUseCase,
  private val checkTaskNameIsUniqueUseCase: CheckTaskNameIsUniqueUseCase,
  private val checkTaskNotExceedResourceQuotaUseCase: CheckTaskNotExceedResourceQuotaUseCase,
  private val updateTaskDependenciesFromLogicConditionUseCase: UpdateTaskDependenciesFromLogicConditionUseCase,
  private val getTaskStatusInfoUseCase: GetTaskStatusInfoUseCase,
  private val sendWorkflowTaskStatusMessageUseCase: SendWorkflowTaskStatusMessageUseCase,
  private val checkTaskDependenciesHasNoCyclesUseCase: CheckTaskDependenciesHasNoCyclesUseCase,
  private val scheduleTaskUseCase: ScheduleTaskUseCase,
  private val taskDtoMapper: TaskDtoMapper
) : UpdateTaskUseCase {

    override fun exec(taskId: Long, taskDto: TaskDto): Task {
        validateTaskUseCase.exec(taskDto)

        val mappedTask = taskDtoMapper.map(taskDto).copy(id = taskId)
        checkTaskNameIsUniqueUseCase.exec(mappedTask)
        checkTaskNotExceedResourceQuotaUseCase.exec(mappedTask)
        checkTaskDependenciesHasNoCyclesUseCase.exec(mappedTask)

        val task = taskRepository.save(mappedTask)
        updateTaskDependenciesFromLogicConditionUseCase.exec(task, taskDto.conditions.logicCondition)

        if (task.scheduled) scheduleTaskUseCase.exec(task)

        val taskStatusInfo = getTaskStatusInfoUseCase.exec(task)
        sendWorkflowTaskStatusMessageUseCase.exec(task.workflow, taskStatusInfo)

        return task
    }
}