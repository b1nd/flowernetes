package ru.flowernetes.orchestration.api.domain.usecase

import ru.flowernetes.entity.task.Task

interface KillTaskUseCase {
    fun exec(task: Task)
}