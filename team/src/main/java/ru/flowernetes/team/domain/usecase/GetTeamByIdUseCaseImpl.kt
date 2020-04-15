package ru.flowernetes.team.domain.usecase

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.flowernetes.entity.team.Team
import ru.flowernetes.team.api.domain.entity.NoSuchTeamException
import ru.flowernetes.team.api.domain.usecase.GetTeamByIdUseCase
import ru.flowernetes.team.data.repo.TeamRepository

@Component
class GetTeamByIdUseCaseImpl(
  private val teamRepository: TeamRepository
) : GetTeamByIdUseCase {

    override fun exec(teamId: Long): Team {
        return teamRepository.findByIdOrNull(teamId) ?: throw NoSuchTeamException(teamId)
    }
}