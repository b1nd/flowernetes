package ru.flowernetes.workflow.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.entity.workload.TaskDuration
import ru.flowernetes.entity.workload.TaskDurationFilter
import ru.flowernetes.task.api.domain.usecase.GetAllTasksByWorkflowUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetWorkflowTasksDurationUseCase
import ru.flowernetes.workload.api.domain.usecase.GetTaskDurationInfoUseCase

@Component
class GetWorkflowTasksDurationUseCaseImpl(
  private val getAllTasksByWorkflowUseCase: GetAllTasksByWorkflowUseCase,
  private val getTaskDurationInfoUseCase: GetTaskDurationInfoUseCase
) : GetWorkflowTasksDurationUseCase {

    override fun exec(workflow: Workflow, taskDurationFilter: TaskDurationFilter): List<TaskDuration> {
        val tasks = getAllTasksByWorkflowUseCase.exec(workflow)
        return tasks.mapNotNull { getTaskDurationInfoUseCase.exec(it, taskDurationFilter) }
    }
}