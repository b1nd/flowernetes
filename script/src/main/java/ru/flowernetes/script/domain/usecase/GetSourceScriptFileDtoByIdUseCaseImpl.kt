package ru.flowernetes.script.domain.usecase

import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.gridfs.GridFsTemplate
import org.springframework.stereotype.Component
import ru.flowernetes.script.api.domain.dto.FileDto
import ru.flowernetes.script.api.domain.usecase.GetSourceScriptFileDtoByIdUseCase

@Component
class GetSourceScriptFileDtoByIdUseCaseImpl(
  private val gridFsTemplate: GridFsTemplate
) : GetSourceScriptFileDtoByIdUseCase {

    override fun exec(id: String): FileDto {
        val gridFSFile = gridFsTemplate.findOne(Query(Criteria.where("_id").`is`(id)))
        val gridFsResource = gridFsTemplate.getResource(gridFSFile)

        return FileDto(
          gridFsResource.filename,
          gridFsResource.contentType,
          gridFsResource.inputStream
        )
    }
}