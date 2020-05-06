package ru.flowernetes.monitoring.api.domain.usecase

import ru.flowernetes.entity.workload.Workload

interface OnWorkflowMonitoringWorkloadUpdateUseCase {
    fun execAsync(workload: Workload)
}