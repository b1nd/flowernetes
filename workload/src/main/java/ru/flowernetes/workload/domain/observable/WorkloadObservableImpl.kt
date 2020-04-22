package ru.flowernetes.workload.domain.observable

import org.springframework.stereotype.Component
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.workload.api.domain.observable.WorkloadObservable
import ru.flowernetes.workload.api.domain.observable.WorkloadObserver

@Component
class WorkloadObservableImpl : WorkloadObservable {
    private val observers: MutableList<WorkloadObserver> = mutableListOf()

    override fun register(workloadObserver: WorkloadObserver) {
        observers.add(workloadObserver)
    }

    override fun remove(workloadObserver: WorkloadObserver) {
        observers.remove(workloadObserver)
    }

    override fun notify(workload: Workload) {
        observers.forEach { it.onUpdate(workload) }
    }
}