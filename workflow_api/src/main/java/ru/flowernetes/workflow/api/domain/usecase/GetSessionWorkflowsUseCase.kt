package ru.flowernetes.workflow.api.domain.usecase

import ru.flowernetes.entity.workflow.Workflow

interface GetSessionWorkflowsUseCase {
    fun exec(): List<Workflow>
}