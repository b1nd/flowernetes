package ru.flowernetes.task.api.domain.usecase

import ru.flowernetes.entity.task.Task

interface UserRunTaskUseCase {
    fun exec(task: Task)
}