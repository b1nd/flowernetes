package ru.flowernetes.task.api.domain.usecase

import ru.flowernetes.entity.task.Task

interface GetSessionTasksUseCase {
    fun exec(): List<Task>
}