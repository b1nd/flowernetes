package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.auth.api.domain.usecase.GetCallingUserSystemRoleUseCase
import ru.flowernetes.entity.auth.SystemUserRole
import ru.flowernetes.entity.task.Task
import ru.flowernetes.orchestration.api.domain.usecase.RunTaskUseCase
import ru.flowernetes.task.api.domain.entity.RunTaskNotAllowedException
import ru.flowernetes.task.api.domain.usecase.UserRunTaskUseCase
import ru.flowernetes.team.api.domain.usecase.IsCallingUserTeamEqualsTeamUseCase

@Component
class UserRunTaskUseCaseImpl(
  private val getCallingUserSystemRoleUseCase: GetCallingUserSystemRoleUseCase,
  private val isCallingUserTeamEqualsTeamUseCase: IsCallingUserTeamEqualsTeamUseCase,
  private val runTaskUseCase: RunTaskUseCase
) : UserRunTaskUseCase {

    override fun exec(task: Task) = if (
      getCallingUserSystemRoleUseCase.execute().role == SystemUserRole.ADMIN ||
      isCallingUserTeamEqualsTeamUseCase.exec(task.workflow.team)
    ) {
        runTaskUseCase.execAsync(task)
    } else {
        throw RunTaskNotAllowedException(task)
    }
}