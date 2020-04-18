package ru.flowernetes.script.domain.usecase

import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.gridfs.GridFsTemplate
import org.springframework.stereotype.Component
import ru.flowernetes.entity.script.SourceScript
import ru.flowernetes.script.api.domain.usecase.GetSourceScriptByIdUseCase
import ru.flowernetes.script.data.mapper.GridFSFileToSourceScriptMapper

@Component
class GetSourceScriptByIdUseCaseImpl(
  private val gridFsTemplate: GridFsTemplate,
  private val gridFSFileToSourceScriptMapper: GridFSFileToSourceScriptMapper
) : GetSourceScriptByIdUseCase {

    override fun exec(id: String): SourceScript {
        val gridFSFile = gridFsTemplate.findOne(Query(Criteria.where("_id").`is`(id)))
        return gridFSFileToSourceScriptMapper.map(gridFSFile)
    }
}