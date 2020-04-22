package ru.flowernetes.workload.api.domain.usecase

import ru.flowernetes.entity.workload.Workload

interface GetWorkloadByIdUseCase {
    fun exec(id: Long): Workload
}