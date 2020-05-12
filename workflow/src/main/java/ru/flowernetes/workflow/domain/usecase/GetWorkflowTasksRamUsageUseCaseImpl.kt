package ru.flowernetes.workflow.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.entity.workload.TasksRamUsage
import ru.flowernetes.workflow.api.domain.usecase.GetWorkflowTasksRamUsageUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetWorkflowTasksResourceUsageUseCase
import java.time.LocalDate

@Component
class GetWorkflowTasksRamUsageUseCaseImpl(
  private val getWorkflowTasksResourceUsageUseCase: GetWorkflowTasksResourceUsageUseCase
) : GetWorkflowTasksRamUsageUseCase {

    override fun exec(workflow: Workflow, from: LocalDate, to: LocalDate): List<TasksRamUsage> {
        return getWorkflowTasksResourceUsageUseCase.exec(
          workflow = workflow,
          from = from,
          to = to,
          selectionRequest = { w -> w.memoryRequest },
          selectionLimit = { w -> w.memoryLimit },
          plus = { a: Long, b: Long -> a + b },
          empty = 0L
        )
    }
}