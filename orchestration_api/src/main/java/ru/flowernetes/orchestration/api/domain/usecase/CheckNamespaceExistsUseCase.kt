package ru.flowernetes.orchestration.api.domain.usecase

interface CheckNamespaceExistsUseCase {
    fun exec(namespaceName: String)
}