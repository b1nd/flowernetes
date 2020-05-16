package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.auth.api.domain.usecase.GetCallingUserSystemRoleUseCase
import ru.flowernetes.entity.auth.SystemUserRole
import ru.flowernetes.entity.task.Task
import ru.flowernetes.task.api.domain.entity.TaskControlNotAllowedException
import ru.flowernetes.task.api.domain.usecase.CheckUserTaskControlAllowedUseCase
import ru.flowernetes.team.api.domain.usecase.IsCallingUserTeamEqualsTeamUseCase

@Component
class CheckUserTaskControlAllowedUseCaseImpl(
  private val getCallingUserSystemRoleUseCase: GetCallingUserSystemRoleUseCase,
  private val isCallingUserTeamEqualsTeamUseCase: IsCallingUserTeamEqualsTeamUseCase
) : CheckUserTaskControlAllowedUseCase {

    override fun exec(task: Task) {
        if ((getCallingUserSystemRoleUseCase.execute().role == SystemUserRole.ADMIN ||
            isCallingUserTeamEqualsTeamUseCase.exec(task.workflow.team)).not()
        ) {
            throw TaskControlNotAllowedException(task)
        }
    }
}