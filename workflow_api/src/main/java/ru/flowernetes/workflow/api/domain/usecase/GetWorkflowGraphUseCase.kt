package ru.flowernetes.workflow.api.domain.usecase

import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workflow.Graph
import ru.flowernetes.entity.workflow.Workflow

interface GetWorkflowGraphUseCase {
    fun exec(workflow: Workflow): Graph<Task, Long>
}