package ru.flowernetes.script.api.domain.usecase

interface CheckSourceScriptHasNoDependenciesUseCase {
    fun exec(sourceScriptId: String)
}