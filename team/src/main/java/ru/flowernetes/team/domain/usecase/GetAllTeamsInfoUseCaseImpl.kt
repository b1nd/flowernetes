package ru.flowernetes.team.domain.usecase

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.flowernetes.entity.orchestration.Namespace
import ru.flowernetes.entity.orchestration.ResourceQuota
import ru.flowernetes.orchestration.api.domain.usecase.GetNamespaceByNameUseCase
import ru.flowernetes.team.api.domain.dto.TeamInfoDto
import ru.flowernetes.team.api.domain.usecase.GetAllTeamsInfoUseCase
import ru.flowernetes.team.data.repo.TeamRepository

@Component
class GetAllTeamsInfoUseCaseImpl(
  private val getNamespaceByNameUseCase: GetNamespaceByNameUseCase,
  private val teamRepository: TeamRepository
) : GetAllTeamsInfoUseCase {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun exec(): List<TeamInfoDto> {
        return teamRepository.findAll().map {
            TeamInfoDto(
              it.id,
              it.name,
              runCatching { getNamespaceByNameUseCase.exec(it.namespace) }.getOrElse { e ->
                  log.error(e.message, e)
                  Namespace(
                    "${it.namespace} not found!",
                    ResourceQuota(0, 0, 0.0, 0.0)
                  )
              }
            )
        }
    }
}