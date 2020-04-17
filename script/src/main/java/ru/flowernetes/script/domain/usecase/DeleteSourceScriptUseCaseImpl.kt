package ru.flowernetes.script.domain.usecase

import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.gridfs.GridFsTemplate
import org.springframework.stereotype.Component
import ru.flowernetes.script.api.domain.usecase.DeleteSourceScriptUseCase

@Component
class DeleteSourceScriptUseCaseImpl(
  private val gridFsTemplate: GridFsTemplate
) : DeleteSourceScriptUseCase {

    override fun exec(id: String) {
        // todo: check if there are no dependencies on this script
        gridFsTemplate.delete(Query(Criteria.where("_id").`is`(id)))
    }
}