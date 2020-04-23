package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.auth.api.domain.entity.NotAllowedException
import ru.flowernetes.entity.task.Task
import ru.flowernetes.scheduling.api.domain.usecase.ScheduleTaskUseCase
import ru.flowernetes.task.api.domain.dto.TaskDto
import ru.flowernetes.task.api.domain.usecase.AddTaskDependenciesFromConditionUseCase
import ru.flowernetes.task.api.domain.usecase.AddTaskUseCase
import ru.flowernetes.task.data.mapper.TaskDtoMapper
import ru.flowernetes.task.data.repo.TaskRepository
import ru.flowernetes.team.api.domain.usecase.GetCallingUserTeamUseCase

@Component
class AddTaskUseCaseImpl(
  private val getCallingUserTeamUseCase: GetCallingUserTeamUseCase,
  private val taskRepository: TaskRepository,
  private val addTaskDependenciesFromConditionUseCase: AddTaskDependenciesFromConditionUseCase,
  private val scheduleTaskUseCase: ScheduleTaskUseCase,
  private val taskDtoMapper: TaskDtoMapper
) : AddTaskUseCase {

    // todo: check condition before saving
    override fun exec(taskDto: TaskDto): Task {
        if (getCallingUserTeamUseCase.exec() != taskDto.workflow.team) {
            throw NotAllowedException("Only team that owns workflow can add tasks")
        }
        val task = taskRepository.save(taskDtoMapper.map(taskDto))

        addTaskDependenciesFromConditionUseCase.exec(task, taskDto.condition)

        if (task.scheduled) scheduleTaskUseCase.exec(task)

        return task
    }
}