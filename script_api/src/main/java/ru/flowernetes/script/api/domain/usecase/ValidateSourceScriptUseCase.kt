package ru.flowernetes.script.api.domain.usecase

import ru.flowernetes.script.api.domain.dto.SourceScriptDto

interface ValidateSourceScriptUseCase {
    fun exec(sourceScript: SourceScriptDto)
}