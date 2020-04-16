package ru.flowernetes.workflow.api.domain.usecase

import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.workflow.api.domain.dto.WorkflowDto

interface AddWorkflowUseCase {
    fun exec(workflowDto: WorkflowDto): Workflow
}