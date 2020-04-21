package ru.flowernetes.orchestration.api.domain.usecase

import ru.flowernetes.entity.task.Task

interface RunTaskUseCase {
    fun exec(task: Task)
}