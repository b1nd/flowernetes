package ru.flowernetes.workflow.api.domain.usecase

import ru.flowernetes.entity.workflow.Workflow

interface GetWorkflowByIdUseCase {
    fun exec(id: Long): Workflow
}