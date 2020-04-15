package ru.flowernetes.team.domain.usecase

import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import ru.flowernetes.auth.api.domain.RoleChecker
import ru.flowernetes.auth.api.domain.requireAll
import ru.flowernetes.entity.auth.SystemUserRole
import ru.flowernetes.orchestration.api.domain.usecase.GetNamespaceByNameUseCase
import ru.flowernetes.team.api.domain.dto.TeamDto
import ru.flowernetes.team.api.domain.dto.TeamInfoDto
import ru.flowernetes.team.api.domain.usecase.AddTeamUseCase
import ru.flowernetes.team.data.mapper.TeamDtoMapper
import ru.flowernetes.team.data.repo.TeamRepository

@Component
class AddTeamUseCaseImpl(
  private val roleChecker: RoleChecker,
  private val teamDtoMapper: TeamDtoMapper,
  private val getNamespaceByNameUseCase: GetNamespaceByNameUseCase,
  private val teamRepository: TeamRepository
) : AddTeamUseCase {

    override fun exec(teamDto: TeamDto): TeamInfoDto {
        roleChecker.requireAll(SystemUserRole.ADMIN)

        if (!StringUtils.hasText(teamDto.name)) {
            throw IllegalArgumentException("Team name cannot be empty")
        }
        val namespace = getNamespaceByNameUseCase.exec(teamDto.namespace)
        val team = teamRepository.save(teamDtoMapper.map(teamDto))

        return TeamInfoDto(
          team.id,
          team.name,
          namespace
        )
    }
}