package ru.flowernetes.team.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.auth.api.domain.RoleChecker
import ru.flowernetes.auth.api.domain.requireAll
import ru.flowernetes.entity.auth.SystemUserRole
import ru.flowernetes.orchestration.api.domain.usecase.GetNamespaceByNameOrEmptyUseCase
import ru.flowernetes.team.api.domain.dto.TeamInfoDto
import ru.flowernetes.team.api.domain.usecase.GetAllTeamsInfoUseCase
import ru.flowernetes.team.data.repo.TeamRepository

@Component
class GetAllTeamsInfoUseCaseImpl(
  private val roleChecker: RoleChecker,
  private val getNamespaceByNameOrEmptyUseCase: GetNamespaceByNameOrEmptyUseCase,
  private val teamRepository: TeamRepository
) : GetAllTeamsInfoUseCase {

    override fun exec(): List<TeamInfoDto> {
        roleChecker.requireAll(SystemUserRole.ADMIN)

        return teamRepository.findAll().map {
            TeamInfoDto(
              it.id,
              it.name,
              getNamespaceByNameOrEmptyUseCase.exec(it.namespace)
            )
        }
    }
}