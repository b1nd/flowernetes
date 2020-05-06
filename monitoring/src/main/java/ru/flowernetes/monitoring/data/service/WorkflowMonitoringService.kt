package ru.flowernetes.monitoring.data.service

import org.springframework.stereotype.Service
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.monitoring.api.domain.usecase.OnWorkflowMonitoringWorkloadUpdateUseCase
import ru.flowernetes.workload.api.domain.observable.WorkloadObservable
import ru.flowernetes.workload.api.domain.observable.WorkloadObserver

@Service
class WorkflowMonitoringService(
  workloadObservable: WorkloadObservable,
  private val onWorkflowMonitoringWorkloadUpdateUseCase: OnWorkflowMonitoringWorkloadUpdateUseCase
) : WorkloadObserver {

    init {
        workloadObservable.register(this)
    }

    override fun onUpdate(workload: Workload) {
        onWorkflowMonitoringWorkloadUpdateUseCase.execAsync(workload)
    }
}