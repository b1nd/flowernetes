package ru.flowernetes.workflow.api.domain.usecase

import ru.flowernetes.entity.workload.TaskDuration
import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.entity.workload.TaskDurationFilter

interface GetWorkflowTasksDurationUseCase {
    fun exec(workflow: Workflow, taskDurationFilter: TaskDurationFilter): List<TaskDuration>
}