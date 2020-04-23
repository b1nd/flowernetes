package ru.flowernetes.task.api.domain.usecase

import ru.flowernetes.entity.task.Task

interface GetAllScheduledTasksUseCase {
    fun exec(): List<Task>
}