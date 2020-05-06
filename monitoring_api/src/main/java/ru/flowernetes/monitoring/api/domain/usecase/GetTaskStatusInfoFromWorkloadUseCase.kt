package ru.flowernetes.monitoring.api.domain.usecase

import ru.flowernetes.entity.monitoring.TaskStatusInfo
import ru.flowernetes.entity.workload.Workload

interface GetTaskStatusInfoFromWorkloadUseCase {
    fun exec(workload: Workload): TaskStatusInfo
}