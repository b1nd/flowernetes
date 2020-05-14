package ru.flowernetes.workflow.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.team.WorkflowsResourceUsage
import ru.flowernetes.entity.workflow.WorkflowResourceUsage
import ru.flowernetes.entity.workload.TasksResourceUsage
import ru.flowernetes.workflow.api.domain.usecase.GetWorkflowsResourceUsageUseCase

@Component
class GetWorkflowsResourceUsageUseCaseImpl : GetWorkflowsResourceUsageUseCase {
    override fun <T, R> exec(
      resourceUsages: List<TasksResourceUsage<T, R>>,
      plus: (R, T) -> R,
      empty: R
    ): List<WorkflowsResourceUsage<R>> = resourceUsages.map { resourceUsage ->
        val usages = resourceUsage.usages
          .groupBy { it.task.workflow }
          .mapValues { (key, values) ->
              WorkflowResourceUsage(
                key,
                tasks = values.map { it.task },
                request = values.fold(empty) { acc, taskResourceUsage -> plus(acc, taskResourceUsage.request) },
                limit = values.fold(empty) { acc, taskResourceUsage -> plus(acc, taskResourceUsage.limit) }
              )
          }.values.toList()

        WorkflowsResourceUsage(
          time = resourceUsage.time,
          usages = usages,
          totalRequest = resourceUsage.totalRequest,
          totalLimit = resourceUsage.totalLimit
        )
    }
}