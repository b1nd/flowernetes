package ru.flowernetes.workflow.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.entity.workload.TimeInterval
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.task.api.domain.usecase.GetAllTasksByWorkflowUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetTasksTimeIntervalsToWorkloadsUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetWorkflowTasksTimeIntervalsToWorkloadsUseCase
import java.time.LocalDate

@Component
class GetWorkflowTasksTimeIntervalsToWorkloadsUseCaseImpl(
  private val getAllTasksByWorkflowUseCase: GetAllTasksByWorkflowUseCase,
  private val getTasksTimeIntervalsToWorkloadsUseCase: GetTasksTimeIntervalsToWorkloadsUseCase
) : GetWorkflowTasksTimeIntervalsToWorkloadsUseCase {

    override fun exec(workflow: Workflow, from: LocalDate, to: LocalDate): Map<TimeInterval, List<Workload>> {
        val workflowTasks = getAllTasksByWorkflowUseCase.exec(workflow)

        return getTasksTimeIntervalsToWorkloadsUseCase.exec(workflowTasks, from, to)
    }
}