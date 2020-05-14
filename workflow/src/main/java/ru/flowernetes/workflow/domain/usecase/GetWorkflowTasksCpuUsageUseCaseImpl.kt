package ru.flowernetes.workflow.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.entity.workload.TasksCpuUsage
import ru.flowernetes.task.api.domain.usecase.GetAllTasksByWorkflowUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetTasksCpuUsageUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetWorkflowTasksCpuUsageUseCase
import java.time.LocalDate

@Component
class GetWorkflowTasksCpuUsageUseCaseImpl(
  private val getAllTasksByWorkflowUseCase: GetAllTasksByWorkflowUseCase,
  private val getTasksCpuUsageUseCase: GetTasksCpuUsageUseCase
) : GetWorkflowTasksCpuUsageUseCase {

    override fun exec(workflow: Workflow, from: LocalDate, to: LocalDate): List<TasksCpuUsage> {
        val workflowTasks = getAllTasksByWorkflowUseCase.exec(workflow)
        return getTasksCpuUsageUseCase.exec(workflowTasks, from, to)
    }
}