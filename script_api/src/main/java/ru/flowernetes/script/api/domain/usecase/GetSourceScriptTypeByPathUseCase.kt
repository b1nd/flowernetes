package ru.flowernetes.script.api.domain.usecase

import ru.flowernetes.entity.containerization.ScriptType

interface GetSourceScriptTypeByPathUseCase {
    fun exec(path: String): ScriptType
}