package ru.flowernetes.containerization.api.domain.usecase

interface GetBaseImageNamesUseCase {
    fun exec(): List<String>
}