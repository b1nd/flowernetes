package ru.flowernetes.script.api.domain.usecase

import ru.flowernetes.entity.containerization.ScriptType

interface GetSourceScriptTypeByIdUseCase {
    fun exec(sourceScriptId: String): ScriptType
}