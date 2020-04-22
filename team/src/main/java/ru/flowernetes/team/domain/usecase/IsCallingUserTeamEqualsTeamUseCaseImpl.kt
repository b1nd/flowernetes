package ru.flowernetes.team.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.team.Team
import ru.flowernetes.team.api.domain.usecase.GetCallingUserTeamUseCase
import ru.flowernetes.team.api.domain.usecase.IsCallingUserTeamEqualsTeamUseCase

@Component
class IsCallingUserTeamEqualsTeamUseCaseImpl(
  private val getCallingUserTeamUseCase: GetCallingUserTeamUseCase
) : IsCallingUserTeamEqualsTeamUseCase {

    override fun exec(team: Team): Boolean = getCallingUserTeamUseCase.exec() == team
}