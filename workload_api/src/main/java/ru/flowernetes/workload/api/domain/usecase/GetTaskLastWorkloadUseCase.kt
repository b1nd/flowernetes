package ru.flowernetes.workload.api.domain.usecase

import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workload.Workload

interface GetTaskLastWorkloadUseCase {
    fun exec(task: Task): Workload?
}