package ru.flowernetes.script.api.domain.usecase

import ru.flowernetes.entity.containerization.ScriptType
import ru.flowernetes.entity.script.SourceScript

interface GetSourceScriptTypeUseCase {
    fun exec(sourceScript: SourceScript): ScriptType
}