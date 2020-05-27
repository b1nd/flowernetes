package ru.flowernetes.script.api.domain.usecase

import ru.flowernetes.entity.script.SourceScript
import ru.flowernetes.pagination.api.domain.entity.Page
import ru.flowernetes.pagination.api.domain.entity.PageRequest
import ru.flowernetes.script.api.domain.dto.SourceScriptFilter

interface GetSourceScriptsUseCase {
    fun exec(pageRequest: PageRequest, sourceScriptFilter: SourceScriptFilter? = null): Page<SourceScript>
}