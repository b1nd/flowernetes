package ru.flowernetes.workflow.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.entity.workload.TasksCpuUsage
import ru.flowernetes.workflow.api.domain.usecase.GetWorkflowTasksCpuUsageUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetWorkflowTasksResourceUsageUseCase
import java.time.LocalDate

@Component
class GetWorkflowTasksCpuUsageUseCaseImpl(
  private val getWorkflowTasksResourceUsageUseCase: GetWorkflowTasksResourceUsageUseCase
) : GetWorkflowTasksCpuUsageUseCase {

    override fun exec(workflow: Workflow, from: LocalDate, to: LocalDate): List<TasksCpuUsage> {
        return getWorkflowTasksResourceUsageUseCase.exec(
          workflow = workflow,
          from = from,
          to = to,
          selectionRequest = { w -> w.cpuRequest },
          selectionLimit = { w -> w.cpuLimit },
          plus = { a: Double, b: Double -> a + b },
          empty = 0.0
        )
    }
}