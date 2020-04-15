package ru.flowernetes.team.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.auth.api.domain.RoleChecker
import ru.flowernetes.auth.api.domain.requireAll
import ru.flowernetes.entity.auth.SystemUserRole
import ru.flowernetes.team.api.domain.usecase.DeleteTeamUseCase
import ru.flowernetes.team.data.repo.TeamRepository

@Component
class DeleteTeamUseCaseImpl(
  private val roleChecker: RoleChecker,
  private val teamRepository: TeamRepository
) : DeleteTeamUseCase {

    override fun exec(teamId: Long) {
        roleChecker.requireAll(SystemUserRole.ADMIN)

        teamRepository.deleteById(teamId)
    }
}