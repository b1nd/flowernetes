package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.scheduling.api.domain.usecase.ScheduleTaskUseCase
import ru.flowernetes.task.api.domain.dto.TaskDto
import ru.flowernetes.task.api.domain.usecase.*
import ru.flowernetes.task.data.mapper.TaskDtoMapper
import ru.flowernetes.task.data.repo.TaskRepository

@Component
class AddTaskUseCaseImpl(
  private val taskRepository: TaskRepository,
  private val validateTaskUseCase: ValidateTaskUseCase,
  private val checkTaskNameIsUniqueUseCase: CheckTaskNameIsUniqueUseCase,
  private val checkTaskNotExceedResourceQuotaUseCase: CheckTaskNotExceedResourceQuotaUseCase,
  private val addTaskDependenciesFromLogicConditionUseCase: AddTaskDependenciesFromLogicConditionUseCase,
  private val scheduleTaskUseCase: ScheduleTaskUseCase,
  private val taskDtoMapper: TaskDtoMapper
) : AddTaskUseCase {

    override fun exec(taskDto: TaskDto): Task {
        validateTaskUseCase.exec(taskDto)

        val mappedTask = taskDtoMapper.map(taskDto)
        checkTaskNameIsUniqueUseCase.exec(mappedTask)
        checkTaskNotExceedResourceQuotaUseCase.exec(mappedTask)

        val task = taskRepository.save(mappedTask)
        taskDto.conditions.logicCondition?.let {
            addTaskDependenciesFromLogicConditionUseCase.exec(task, it)
        }
        if (task.scheduled) scheduleTaskUseCase.exec(task)

        return task
    }
}