package ru.flowernetes.workflow.api.domain.usecase

import ru.flowernetes.workflow.api.domain.dto.WorkflowDto

interface ValidateWorkflowUseCase {
    fun exec(workflowDto: WorkflowDto)
}