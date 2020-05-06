package ru.flowernetes.scheduling.api.domain.usecase

import ru.flowernetes.entity.workload.Workload

interface OnSchedulingWorkloadUpdateUseCase {
    fun execAsync(workload: Workload)
}