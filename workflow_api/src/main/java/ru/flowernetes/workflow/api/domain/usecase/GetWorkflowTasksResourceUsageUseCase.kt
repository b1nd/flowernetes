package ru.flowernetes.workflow.api.domain.usecase

import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.entity.workload.TasksResourceUsage
import ru.flowernetes.entity.workload.Workload
import java.time.LocalDate

interface GetWorkflowTasksResourceUsageUseCase {
    fun <T, R> exec(
      workflow: Workflow,
      from: LocalDate,
      to: LocalDate,
      selectionRequest: (Workload) -> T,
      selectionLimit: (Workload) -> T,
      plus: (R, T) -> R,
      empty: R
    ): List<TasksResourceUsage<T, R>>
}