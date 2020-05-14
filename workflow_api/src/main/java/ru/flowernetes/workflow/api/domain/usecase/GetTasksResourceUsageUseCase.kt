package ru.flowernetes.workflow.api.domain.usecase

import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workload.TasksResourceUsage
import ru.flowernetes.entity.workload.Workload
import java.time.LocalDate

interface GetTasksResourceUsageUseCase {
    fun <T, R> exec(
      tasks: List<Task>,
      from: LocalDate,
      to: LocalDate,
      selectionRequest: (Workload) -> T,
      selectionLimit: (Workload) -> T,
      plus: (R, T) -> R,
      empty: R
    ): List<TasksResourceUsage<T, R>>
}