package ru.flowernetes.script.api.domain.usecase

interface DeleteSourceScriptUseCase {
    fun exec(id: String)
}