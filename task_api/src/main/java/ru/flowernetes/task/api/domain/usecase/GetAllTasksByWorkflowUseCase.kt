package ru.flowernetes.task.api.domain.usecase

import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workflow.Workflow

interface GetAllTasksByWorkflowUseCase {
    fun exec(workflow: Workflow): List<Task>
}