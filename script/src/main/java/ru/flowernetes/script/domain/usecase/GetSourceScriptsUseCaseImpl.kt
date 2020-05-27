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
import ru.flowernetes.script.api.domain.dto.SourceScriptFilter
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

    override fun exec(pageRequest: PageRequest, sourceScriptFilter: SourceScriptFilter?): Page<SourceScript> {
        val pageable = pageRequest.copy(
          sort = pageRequest.sort.copy(
            orders = pageRequest.sort.orders.map {
                it.copy(
                  property = when (val prop = it.property) {
                      "id" -> "_id"
                      "name" -> ScriptMetadataKeys.NAME.key
                      "tag" -> ScriptMetadataKeys.TAG.key
                      "runFilePath" -> ScriptMetadataKeys.RUN_FILE_PATH.key
                      "teamId" -> ScriptMetadataKeys.TEAM_ID.key
                      "isPublic" -> ScriptMetadataKeys.IS_PUBLIC.key
                      else -> prop
                  })
            })).toSpringPageRequest()
        val sourceCriteria = Criteria.where(ScriptMetadataKeys.SOURCE.key).exists(true)

        val userCriteria = when (getCallingUserSystemRoleUseCase.execute().role) {
            SystemUserRole.TEAM -> {
                val callingTeamId = getCallingUserTeamUseCase.exec().id
                sourceCriteria.orOperator(
                  Criteria.where(ScriptMetadataKeys.TEAM_ID.key).`is`(callingTeamId),
                  Criteria.where(ScriptMetadataKeys.IS_PUBLIC.key).`is`(true)
                )
            }
            SystemUserRole.ADMIN -> sourceCriteria
        }
        val criteria = userCriteria.addFilter(sourceScriptFilter)

        val files = gridFsTemplate.find(Query(criteria).with(pageable))
        val itemsCount = files.count().toLong()
        val items = files
          .skip(pageable.offset.toInt())
          .limit(pageable.pageSize)
          .toList()
          .map(gridFSFileToSourceScriptMapper::map)

        return PageImpl(
          items,
          pageable,
          itemsCount
        ).toPage()
    }

    private fun Criteria.addFilter(sourceScriptFilter: SourceScriptFilter?): Criteria {
        sourceScriptFilter ?: return this
        val idCriteria = sourceScriptFilter.id?.let {
            Criteria.where("_id").`is`(it)
        }
        val nameCriteria = sourceScriptFilter.name?.let {
            Criteria.where(ScriptMetadataKeys.NAME.key).regex(".*$it.*")
        }
        val tagCriteria = sourceScriptFilter.tag?.let {
            Criteria.where(ScriptMetadataKeys.TAG.key).regex(".*$it.*")
        }
        val filterCriteria = listOfNotNull(idCriteria, nameCriteria, tagCriteria)

        if (filterCriteria.isEmpty()) return this

        return andOperator(*filterCriteria.toTypedArray())
    }
}