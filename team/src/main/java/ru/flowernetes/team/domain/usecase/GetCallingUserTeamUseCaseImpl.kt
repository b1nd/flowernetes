package ru.flowernetes.team.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.auth.api.domain.RoleChecker
import ru.flowernetes.auth.api.domain.requireAll
import ru.flowernetes.auth.api.domain.usecase.GetCallingUserUseCase
import ru.flowernetes.entity.auth.SystemUserRole
import ru.flowernetes.entity.team.Team
import ru.flowernetes.team.api.domain.usecase.GetCallingUserTeamUseCase
import ru.flowernetes.team.api.domain.usecase.GetTeamByUserUseCase

@Component
class GetCallingUserTeamUseCaseImpl(
  private val roleChecker: RoleChecker,
  private val getCallingUserUseCase: GetCallingUserUseCase,
  private val getTeamByUserUseCase: GetTeamByUserUseCase
) : GetCallingUserTeamUseCase {

    override fun exec(): Team {
        roleChecker.requireAll(SystemUserRole.TEAM)

        val user = getCallingUserUseCase.execute()
        return getTeamByUserUseCase.exec(user)
    }
}