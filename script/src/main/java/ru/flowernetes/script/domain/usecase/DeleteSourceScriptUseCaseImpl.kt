package ru.flowernetes.script.domain.usecase

import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.gridfs.GridFsTemplate
import org.springframework.stereotype.Component
import ru.flowernetes.script.api.domain.usecase.CheckSourceScriptHasNoDependenciesUseCase
import ru.flowernetes.script.api.domain.usecase.DeleteSourceScriptUseCase

@Component
class DeleteSourceScriptUseCaseImpl(
  private val gridFsTemplate: GridFsTemplate,
  private val checkSourceScriptHasNoDependenciesUseCase: CheckSourceScriptHasNoDependenciesUseCase
) : DeleteSourceScriptUseCase {

    override fun exec(id: String) {
        checkSourceScriptHasNoDependenciesUseCase.exec(id)
        gridFsTemplate.delete(Query(Criteria.where("_id").`is`(id)))
    }
}