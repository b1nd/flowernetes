package ru.flowernetes.containerization.api.domain.usecase

import ru.flowernetes.entity.task.Task

interface GetTaskImageOrCreateUseCase {
    fun exec(task: Task): String
}