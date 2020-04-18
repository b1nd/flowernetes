package ru.flowernetes.script.domain.usecase

import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.gridfs.GridFsTemplate
import org.springframework.stereotype.Component
import ru.flowernetes.entity.script.SourceScript
import ru.flowernetes.entity.team.Team
import ru.flowernetes.script.api.domain.entity.ScriptMetadataKeys
import ru.flowernetes.script.api.domain.usecase.GetAllTeamSourceScriptsUseCase
import ru.flowernetes.script.data.mapper.GridFSFileToSourceScriptMapper

@Component
class GetAllTeamSourceScriptsUseCaseImpl(
  private val gridFsTemplate: GridFsTemplate,
  private val gridFSFileToSourceScriptMapper: GridFSFileToSourceScriptMapper
) : GetAllTeamSourceScriptsUseCase {

    override fun exec(team: Team): List<SourceScript> {
        val criteria = Criteria.where(ScriptMetadataKeys.SOURCE.key).exists(true).andOperator(
          Criteria.where(ScriptMetadataKeys.TEAM_ID.key).`is`(team.id)
        )
        return gridFsTemplate
          .find(Query(criteria).with(Sort.by(Sort.Direction.DESC, "uploadDate")))
          .map(gridFSFileToSourceScriptMapper::map)
          .toList()
    }
}