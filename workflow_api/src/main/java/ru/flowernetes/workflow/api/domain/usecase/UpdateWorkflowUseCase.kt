package ru.flowernetes.workflow.api.domain.usecase

import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.workflow.api.domain.dto.WorkflowDto

interface UpdateWorkflowUseCase {
    fun exec(workflowId: Long, workflowDto: WorkflowDto): Workflow
}