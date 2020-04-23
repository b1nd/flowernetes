package ru.flowernetes.workload.api.domain.usecase

import ru.flowernetes.entity.task.Task

interface DeleteWorkloadsByTaskUseCase {
    fun exec(task: Task)
}