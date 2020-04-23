package ru.flowernetes.scheduling.data.service

import org.springframework.stereotype.Service
import ru.flowernetes.entity.task.TaskStatus
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.orchestration.api.domain.usecase.RunTaskUseCase
import ru.flowernetes.scheduling.api.domain.usecase.ScheduleTaskUseCase
import ru.flowernetes.task.api.domain.usecase.GetAllScheduledTasksUseCase
import ru.flowernetes.task.api.domain.usecase.IsTaskConditionSatisfiedUseCase
import ru.flowernetes.task.api.domain.usecase.MarkAllDependentTasksAndGetThemUseCase
import ru.flowernetes.task.api.domain.usecase.UnmarkTaskDependencyMarkersUseCase
import ru.flowernetes.workload.api.domain.observable.WorkloadObservable
import ru.flowernetes.workload.api.domain.observable.WorkloadObserver

@Service
class SchedulingService(
  workloadObservable: WorkloadObservable,
  private val getAllScheduledTasksUseCase: GetAllScheduledTasksUseCase,
  private val unmarkTaskDependencyMarkersUseCase: UnmarkTaskDependencyMarkersUseCase,
  private val scheduleTaskUseCase: ScheduleTaskUseCase,
  private val markAllDependentTasksAndGetThemUseCase: MarkAllDependentTasksAndGetThemUseCase,
  private val isTaskConditionSatisfiedUseCase: IsTaskConditionSatisfiedUseCase,
  private val runTaskUseCase: RunTaskUseCase
) : WorkloadObserver {

    init {
        workloadObservable.register(this)
        scheduleAllTasks()
    }

    override fun onUpdate(workload: Workload) {
        when (workload.taskStatus) {
            TaskStatus.PENDING -> {
                unmarkTaskDependencyMarkersUseCase.exec(workload.task)
            }
            TaskStatus.SUCCESS -> {
                val dependentTasks = markAllDependentTasksAndGetThemUseCase.exec(workload.task)
                dependentTasks.forEach {
                    if (isTaskConditionSatisfiedUseCase.exec(it)) {
                        runTaskUseCase.execAsync(it)
                    }
                }
            }
        }
    }

    private fun scheduleAllTasks() {
        getAllScheduledTasksUseCase.exec().forEach(scheduleTaskUseCase::exec)
    }
}