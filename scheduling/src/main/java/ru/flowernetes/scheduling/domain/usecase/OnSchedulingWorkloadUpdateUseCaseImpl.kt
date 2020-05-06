package ru.flowernetes.scheduling.domain.usecase

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.TaskStatus
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.orchestration.api.domain.usecase.RunTaskUseCase
import ru.flowernetes.scheduling.api.domain.usecase.OnSchedulingWorkloadUpdateUseCase
import ru.flowernetes.task.api.domain.usecase.IsTaskConditionSatisfiedUseCase
import ru.flowernetes.task.api.domain.usecase.MarkAllDependentTasksAndGetThemUseCase
import ru.flowernetes.task.api.domain.usecase.UnmarkTaskDependencyMarkersUseCase

@Component
open class OnSchedulingWorkloadUpdateUseCaseImpl(
  private val markAllDependentTasksAndGetThemUseCase: MarkAllDependentTasksAndGetThemUseCase,
  private val isTaskConditionSatisfiedUseCase: IsTaskConditionSatisfiedUseCase,
  private val unmarkTaskDependencyMarkersUseCase: UnmarkTaskDependencyMarkersUseCase,
  private val runTaskUseCase: RunTaskUseCase
) : OnSchedulingWorkloadUpdateUseCase {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Async
    override fun execAsync(workload: Workload) {
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
            else -> log.debug("Skipping update $workload")
        }
    }
}