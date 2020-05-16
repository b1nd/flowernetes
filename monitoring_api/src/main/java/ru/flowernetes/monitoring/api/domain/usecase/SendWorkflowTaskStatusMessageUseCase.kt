package ru.flowernetes.monitoring.api.domain.usecase

import ru.flowernetes.entity.monitoring.TaskStatusInfo
import ru.flowernetes.entity.workflow.Workflow

interface SendWorkflowTaskStatusMessageUseCase {
    fun exec(workflow: Workflow, taskStatusInfo: TaskStatusInfo)
}