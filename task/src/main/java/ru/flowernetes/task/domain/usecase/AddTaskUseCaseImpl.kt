package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.auth.api.domain.entity.NotAllowedException
import ru.flowernetes.entity.containerization.ScriptType
import ru.flowernetes.entity.task.Task
import ru.flowernetes.scheduling.api.domain.usecase.ScheduleTaskUseCase
import ru.flowernetes.script.api.domain.usecase.GetSourceScriptTypeByIdUseCase
import ru.flowernetes.task.api.domain.dto.TaskDto
import ru.flowernetes.task.api.domain.entity.TaskValidationException
import ru.flowernetes.task.api.domain.usecase.AddTaskDependenciesFromLogicConditionUseCase
import ru.flowernetes.task.api.domain.usecase.AddTaskUseCase
import ru.flowernetes.task.data.mapper.TaskDtoMapper
import ru.flowernetes.task.data.repo.TaskRepository
import ru.flowernetes.team.api.domain.usecase.GetCallingUserTeamUseCase

@Component
class AddTaskUseCaseImpl(
  private val getCallingUserTeamUseCase: GetCallingUserTeamUseCase,
  private val taskRepository: TaskRepository,
  private val addTaskDependenciesFromLogicConditionUseCase: AddTaskDependenciesFromLogicConditionUseCase,
  private val scheduleTaskUseCase: ScheduleTaskUseCase,
  private val getSourceScriptTypeByIdUseCase: GetSourceScriptTypeByIdUseCase,
  private val taskDtoMapper: TaskDtoMapper
) : AddTaskUseCase {

    override fun exec(taskDto: TaskDto): Task {
        if (getCallingUserTeamUseCase.exec() != taskDto.workflow.team) {
            throw NotAllowedException("Only team that owns workflow can add tasks")
        }
        if (taskDto.saveScript && getSourceScriptTypeByIdUseCase.exec(taskDto.sourceScriptId) == ScriptType.PY) {
            throw TaskValidationException("Python script output cannot be saved")
        }
        val task = taskRepository.save(taskDtoMapper.map(taskDto))

        taskDto.conditions.logicCondition?.let {
            addTaskDependenciesFromLogicConditionUseCase.exec(task, it)
        }

        if (task.scheduled) scheduleTaskUseCase.exec(task)

        return task
    }
}