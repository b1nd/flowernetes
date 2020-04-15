package ru.flowernetes.team.domain.usecase

import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import ru.flowernetes.auth.api.domain.RoleChecker
import ru.flowernetes.auth.api.domain.requireAll
import ru.flowernetes.entity.auth.SystemUserRole
import ru.flowernetes.orchestration.api.domain.usecase.GetNamespaceByNameUseCase
import ru.flowernetes.team.api.domain.dto.TeamInfoDto
import ru.flowernetes.team.api.domain.dto.UpdateTeamDto
import ru.flowernetes.team.api.domain.usecase.GetTeamByIdUseCase
import ru.flowernetes.team.api.domain.usecase.UpdateTeamUseCase
import ru.flowernetes.team.data.repo.TeamRepository

@Component
class UpdateTeamUseCaseImpl(
  private val roleChecker: RoleChecker,
  private val teamRepository: TeamRepository,
  private val getNamespaceByNameUseCase: GetNamespaceByNameUseCase,
  private val getTeamByIdUseCase: GetTeamByIdUseCase
) : UpdateTeamUseCase {

    override fun exec(teamId: Long, updateTeamDto: UpdateTeamDto): TeamInfoDto {
        roleChecker.requireAll(SystemUserRole.ADMIN)

        val team = getTeamByIdUseCase.exec(teamId)

        updateTeamDto.name?.let {
            if (!StringUtils.hasText(it)) {
                throw IllegalArgumentException("Team name cannot be empty")
            }
        }
        val namespace = getNamespaceByNameUseCase.exec(
          updateTeamDto.namespace ?: team.namespace
        )
        val newTeam = teamRepository.save(team.copy(
          name = updateTeamDto.name ?: team.name,
          namespace = updateTeamDto.namespace ?: team.namespace
        ))

        return TeamInfoDto(
          newTeam.id,
          newTeam.name,
          namespace
        )
    }
}