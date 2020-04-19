package ru.flowernetes.task.api.domain.usecase

import ru.flowernetes.entity.task.Task

interface GetTaskByIdUseCase {
    fun exec(id: Long): Task
}