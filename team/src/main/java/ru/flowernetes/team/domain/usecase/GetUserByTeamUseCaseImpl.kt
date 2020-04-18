package ru.flowernetes.team.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.auth.User
import ru.flowernetes.entity.team.Team
import ru.flowernetes.team.api.domain.entity.NoUserLinkedWithTeamException
import ru.flowernetes.team.api.domain.usecase.GetUserByTeamUseCase
import ru.flowernetes.team.data.repo.TeamUserRepository

@Component
class GetUserByTeamUseCaseImpl(
  private val teamUserRepository: TeamUserRepository
) : GetUserByTeamUseCase {

    override fun exec(team: Team): User {
        val teamUser = teamUserRepository.findByTeam(team) ?: throw NoUserLinkedWithTeamException(team)
        return teamUser.user
    }
}