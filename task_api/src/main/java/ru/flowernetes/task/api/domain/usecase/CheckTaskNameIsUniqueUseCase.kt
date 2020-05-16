package ru.flowernetes.task.api.domain.usecase

import ru.flowernetes.entity.task.Task

interface CheckTaskNameIsUniqueUseCase {
    fun exec(task: Task)
}