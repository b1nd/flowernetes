package ru.flowernetes.workflow.api.domain.usecase

interface DeleteWorkflowUseCase {
    fun exec(workflowId: Long)
}