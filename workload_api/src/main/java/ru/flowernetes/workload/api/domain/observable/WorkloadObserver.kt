package ru.flowernetes.workload.api.domain.observable

import ru.flowernetes.entity.workload.Workload

interface WorkloadObserver {
    fun onUpdate(workload: Workload)
}