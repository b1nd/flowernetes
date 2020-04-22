package ru.flowernetes.workload.api.domain.usecase

import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.workload.api.domain.model.WorkloadModel

interface AddWorkloadUseCase {
    fun exec(workloadModel: WorkloadModel): Workload
}