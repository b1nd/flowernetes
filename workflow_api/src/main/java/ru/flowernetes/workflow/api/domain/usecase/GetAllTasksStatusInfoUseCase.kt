package ru.flowernetes.workflow.api.domain.usecase

import ru.flowernetes.entity.monitoring.TaskStatusInfo
import ru.flowernetes.entity.workflow.Workflow

interface GetAllTasksStatusInfoUseCase {
    fun exec(workflow: Workflow): List<TaskStatusInfo>
}