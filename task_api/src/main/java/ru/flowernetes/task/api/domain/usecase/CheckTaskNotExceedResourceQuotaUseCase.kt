package ru.flowernetes.task.api.domain.usecase

import ru.flowernetes.entity.task.Task

interface CheckTaskNotExceedResourceQuotaUseCase {
    fun exec(task: Task)
}