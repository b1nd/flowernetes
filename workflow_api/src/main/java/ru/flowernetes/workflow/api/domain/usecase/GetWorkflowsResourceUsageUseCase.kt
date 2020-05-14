package ru.flowernetes.workflow.api.domain.usecase

import ru.flowernetes.entity.team.WorkflowsResourceUsage
import ru.flowernetes.entity.workload.TasksResourceUsage

interface GetWorkflowsResourceUsageUseCase {
    fun <T, R> exec(
      resourceUsages: List<TasksResourceUsage<T, R>>,
      plus: (R, T) -> R,
      empty: R
    ): List<WorkflowsResourceUsage<R>>
}