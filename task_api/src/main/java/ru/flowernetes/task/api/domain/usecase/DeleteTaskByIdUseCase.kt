package ru.flowernetes.task.api.domain.usecase

interface DeleteTaskByIdUseCase {
    fun exec(id: Long)
}