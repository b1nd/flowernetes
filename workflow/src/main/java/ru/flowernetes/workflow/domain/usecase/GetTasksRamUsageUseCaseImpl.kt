package ru.flowernetes.workflow.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workload.TasksRamUsage
import ru.flowernetes.workflow.api.domain.usecase.GetTasksRamUsageUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetTasksResourceUsageUseCase
import java.time.LocalDate

@Component
class GetTasksRamUsageUseCaseImpl(
  private val getTasksResourceUsageUseCase: GetTasksResourceUsageUseCase
) : GetTasksRamUsageUseCase {

    override fun exec(tasks: List<Task>, from: LocalDate, to: LocalDate): List<TasksRamUsage> {
        return getTasksResourceUsageUseCase.exec(
          tasks = tasks,
          from = from,
          to = to,
          selectionRequest = { w -> w.memoryRequest },
          selectionLimit = { w -> w.memoryLimit },
          plus = { a, b -> a + b },
          empty = 0L
        )
    }
}