package ru.flowernetes.script.domain.usecase

import org.springframework.data.domain.PageImpl
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.gridfs.GridFsTemplate
import org.springframework.stereotype.Component
import ru.flowernetes.auth.api.domain.usecase.GetCallingUserSystemRoleUseCase
import ru.flowernetes.entity.auth.SystemUserRole
import ru.flowernetes.entity.script.SourceScript
import ru.flowernetes.pagination.api.domain.entity.Page
import ru.flowernetes.pagination.api.domain.entity.PageRequest
import ru.flowernetes.script.api.domain.entity.ScriptMetadataKeys
import ru.flowernetes.script.api.domain.usecase.GetSourceScriptsUseCase
import ru.flowernetes.script.data.mapper.GridFSFileToSourceScriptMapper
import ru.flowernetes.team.api.domain.usecase.GetCallingUserTeamUseCase
import ru.flowernetes.util.extensions.toPage
import ru.flowernetes.util.extensions.toSpringPageRequest


@Component
class GetSourceScriptsUseCaseImpl(
  private val gridFsTemplate: GridFsTemplate,
  private val gridFSFileToSourceScriptMapper: GridFSFileToSourceScriptMapper,
  private val getCallingUserSystemRoleUseCase: GetCallingUserSystemRoleUseCase,
  private val getCallingUserTeamUseCase: GetCallingUserTeamUseCase
) : GetSourceScriptsUseCase {

    override fun exec(pageRequest: PageRequest): Page<SourceScript> {
        val pageable = pageRequest.toSpringPageRequest()
        val sourceCriteria = Criteria.where(ScriptMetadataKeys.SOURCE.key).exists(true)

        val criteria = when (getCallingUserSystemRoleUseCase.execute().role) {
            SystemUserRole.TEAM -> {
                val callingTeamId = getCallingUserTeamUseCase.exec().id
                sourceCriteria.orOperator(
                  Criteria.where(ScriptMetadataKeys.TEAM_ID.key).`is`(callingTeamId),
                  Criteria.where(ScriptMetadataKeys.IS_PUBLIC.key).`is`(true)
                )
            }
            SystemUserRole.ADMIN -> sourceCriteria
        }

        // fixme: Pageable with Query dont work???
        val items = gridFsTemplate.find(Query(criteria).with(pageable.sort)).toList()
        val itemsCount = items.count()

        val start = pageable.offset.toInt()
        val end = if (start + pageable.pageSize > itemsCount) itemsCount else start + pageable.pageSize

        return PageImpl(
          items.subList(start, end).map(gridFSFileToSourceScriptMapper::map),
          pageable,
          itemsCount.toLong()
        ).toPage()
    }
}