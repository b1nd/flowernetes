package ru.flowernetes.orchestration.api.domain.usecase

interface GetNamespaceNamesUseCase {
    fun exec(): List<String>
}