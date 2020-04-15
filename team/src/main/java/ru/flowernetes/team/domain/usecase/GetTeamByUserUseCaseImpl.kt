package ru.flowernetes.team.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.auth.User
import ru.flowernetes.entity.team.Team
import ru.flowernetes.team.api.domain.entity.NoTeamLinkedWithUserException
import ru.flowernetes.team.api.domain.usecase.GetTeamByUserUseCase
import ru.flowernetes.team.data.repo.TeamUserRepository

@Component
class GetTeamByUserUseCaseImpl(
  private val teamUserRepository: TeamUserRepository
) : GetTeamByUserUseCase {

    override fun exec(user: User): Team {
        val teamUser = teamUserRepository.findByUser(user) ?: throw NoTeamLinkedWithUserException(user)
        return teamUser.team
    }
}