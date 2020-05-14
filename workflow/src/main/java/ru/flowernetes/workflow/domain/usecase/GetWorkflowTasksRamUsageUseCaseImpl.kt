package ru.flowernetes.workflow.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.entity.workload.TasksRamUsage
import ru.flowernetes.task.api.domain.usecase.GetAllTasksByWorkflowUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetTasksRamUsageUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetWorkflowTasksRamUsageUseCase
import java.time.LocalDate

@Component
class GetWorkflowTasksRamUsageUseCaseImpl(
  private val getAllTasksByWorkflowUseCase: GetAllTasksByWorkflowUseCase,
  private val getTasksRamUsageUseCase: GetTasksRamUsageUseCase
) : GetWorkflowTasksRamUsageUseCase {

    override fun exec(workflow: Workflow, from: LocalDate, to: LocalDate): List<TasksRamUsage> {
        val workflowTasks = getAllTasksByWorkflowUseCase.exec(workflow)
        return getTasksRamUsageUseCase.exec(workflowTasks, from, to)
    }
}