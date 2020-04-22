package ru.flowernetes.workload.api.domain.usecase

import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.pagination.api.domain.entity.Page
import ru.flowernetes.pagination.api.domain.entity.PageRequest
import ru.flowernetes.workload.api.domain.dto.WorkloadFilter

interface GetWorkloadsUseCase {
    fun exec(pageRequest: PageRequest, workloadFilter: WorkloadFilter? = null): Page<Workload>
}