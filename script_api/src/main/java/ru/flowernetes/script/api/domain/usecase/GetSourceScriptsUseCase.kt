package ru.flowernetes.script.api.domain.usecase

import ru.flowernetes.entity.script.SourceScript
import ru.flowernetes.pagination.api.domain.entity.Page
import ru.flowernetes.pagination.api.domain.entity.PageRequest

interface GetSourceScriptsUseCase {
    fun exec(pageRequest: PageRequest): Page<SourceScript>
}