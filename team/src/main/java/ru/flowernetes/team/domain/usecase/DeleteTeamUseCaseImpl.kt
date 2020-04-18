package ru.flowernetes.team.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.auth.api.domain.RoleChecker
import ru.flowernetes.auth.api.domain.requireAll
import ru.flowernetes.auth.api.domain.usecase.DeleteUserUseCase
import ru.flowernetes.entity.auth.SystemUserRole
import ru.flowernetes.team.api.domain.usecase.DeleteTeamUseCase
import ru.flowernetes.team.api.domain.usecase.GetTeamByIdUseCase
import ru.flowernetes.team.api.domain.usecase.GetUserByTeamUseCase
import ru.flowernetes.team.data.repo.TeamRepository

@Component
class DeleteTeamUseCaseImpl(
  private val roleChecker: RoleChecker,
  private val deleteUserUseCase: DeleteUserUseCase,
  private val getUserByTeamUseCase: GetUserByTeamUseCase,
  private val getTeamByIdUseCase: GetTeamByIdUseCase,
  private val teamRepository: TeamRepository
) : DeleteTeamUseCase {

    // todo: check if there are no dependencies on this team
    override fun exec(teamId: Long) {
        roleChecker.requireAll(SystemUserRole.ADMIN)
        val team = getTeamByIdUseCase.exec(teamId)
        val user = getUserByTeamUseCase.exec(team)

        teamRepository.deleteById(teamId)
        deleteUserUseCase.execute(user)
    }
}