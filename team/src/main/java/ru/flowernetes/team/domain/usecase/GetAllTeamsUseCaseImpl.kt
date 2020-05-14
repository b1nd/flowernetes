package ru.flowernetes.team.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.auth.api.domain.RoleChecker
import ru.flowernetes.auth.api.domain.requireAll
import ru.flowernetes.entity.auth.SystemUserRole
import ru.flowernetes.entity.team.Team
import ru.flowernetes.team.api.domain.usecase.GetAllTeamsUseCase
import ru.flowernetes.team.data.repo.TeamRepository

@Component
class GetAllTeamsUseCaseImpl(
  private val roleChecker: RoleChecker,
  private val teamRepository: TeamRepository
) : GetAllTeamsUseCase {

    override fun exec(): List<Team> {
        roleChecker.requireAll(SystemUserRole.ADMIN)

        return teamRepository.findAll()
    }
}