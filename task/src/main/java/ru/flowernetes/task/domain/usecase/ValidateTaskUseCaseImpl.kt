package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.auth.api.domain.entity.NotAllowedException
import ru.flowernetes.entity.containerization.ScriptType
import ru.flowernetes.script.api.domain.usecase.GetSourceScriptTypeByIdUseCase
import ru.flowernetes.task.api.domain.dto.TaskDto
import ru.flowernetes.task.api.domain.entity.TaskValidationException
import ru.flowernetes.task.api.domain.usecase.ValidateTaskUseCase
import ru.flowernetes.team.api.domain.usecase.GetCallingUserTeamUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetWorkflowByIdUseCase

@Component
class ValidateTaskUseCaseImpl(
  private val getCallingUserTeamUseCase: GetCallingUserTeamUseCase,
  private val getWorkflowByIdUseCase: GetWorkflowByIdUseCase,
  private val getSourceScriptTypeByIdUseCase: GetSourceScriptTypeByIdUseCase
) : ValidateTaskUseCase {

    override fun exec(taskDto: TaskDto) {
        val workflow = getWorkflowByIdUseCase.exec(taskDto.workflowId)
        if (getCallingUserTeamUseCase.exec() != workflow.team) {
            throw NotAllowedException("Only team that owns workflow can add tasks to it")
        }
        if (taskDto.saveScript && getSourceScriptTypeByIdUseCase.exec(taskDto.sourceScriptId) == ScriptType.PY) {
            throw TaskValidationException("Python script output cannot be saved")
        }
    }
}