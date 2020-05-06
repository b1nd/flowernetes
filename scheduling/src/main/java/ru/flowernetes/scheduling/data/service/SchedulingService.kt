package ru.flowernetes.scheduling.data.service

import org.springframework.stereotype.Service
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.scheduling.api.domain.usecase.OnSchedulingWorkloadUpdateUseCase
import ru.flowernetes.scheduling.api.domain.usecase.ScheduleTaskUseCase
import ru.flowernetes.task.api.domain.usecase.GetAllScheduledTasksUseCase
import ru.flowernetes.workload.api.domain.observable.WorkloadObservable
import ru.flowernetes.workload.api.domain.observable.WorkloadObserver

@Service
class SchedulingService(
  workloadObservable: WorkloadObservable,
  private val onSchedulingWorkloadUpdateUseCase: OnSchedulingWorkloadUpdateUseCase,
  private val getAllScheduledTasksUseCase: GetAllScheduledTasksUseCase,
  private val scheduleTaskUseCase: ScheduleTaskUseCase
) : WorkloadObserver {

    init {
        workloadObservable.register(this)
        scheduleAllTasks()
    }

    override fun onUpdate(workload: Workload) {
        onSchedulingWorkloadUpdateUseCase.execAsync(workload)
    }

    private fun scheduleAllTasks() {
        getAllScheduledTasksUseCase.exec().forEach(scheduleTaskUseCase::exec)
    }
}