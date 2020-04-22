package ru.flowernetes.workload.api.domain.observable

import ru.flowernetes.entity.workload.Workload

interface WorkloadObservable {
    fun register(workloadObserver: WorkloadObserver)
    fun remove(workloadObserver: WorkloadObserver)
    fun notify(workload: Workload)
}