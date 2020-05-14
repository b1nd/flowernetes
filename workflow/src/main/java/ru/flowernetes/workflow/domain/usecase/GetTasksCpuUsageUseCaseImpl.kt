package ru.flowernetes.workflow.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workload.TasksCpuUsage
import ru.flowernetes.workflow.api.domain.usecase.GetTasksCpuUsageUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetTasksResourceUsageUseCase
import java.time.LocalDate

@Component
class GetTasksCpuUsageUseCaseImpl(
  private val getTasksResourceUsageUseCase: GetTasksResourceUsageUseCase
) : GetTasksCpuUsageUseCase {

    override fun exec(tasks: List<Task>, from: LocalDate, to: LocalDate): List<TasksCpuUsage> {
        return getTasksResourceUsageUseCase.exec(
          tasks = tasks,
          from = from,
          to = to,
          selectionRequest = { w -> w.cpuRequest },
          selectionLimit = { w -> w.cpuLimit },
          plus = { a, b -> a + b },
          empty = 0.0
        )
    }
}