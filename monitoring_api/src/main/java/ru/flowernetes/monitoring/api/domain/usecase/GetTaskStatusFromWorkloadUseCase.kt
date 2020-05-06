package ru.flowernetes.monitoring.api.domain.usecase

import ru.flowernetes.entity.task.TaskStatus
import ru.flowernetes.entity.workload.Workload

interface GetTaskStatusFromWorkloadUseCase {
    fun exec(workload: Workload): TaskStatus
}