package ru.flowernetes.workload.api.domain.usecase

import ru.flowernetes.entity.workload.Workload

interface UpdateWorkloadUseCase {
    fun exec(workload: Workload): Workload
}