package ru.flowernetes.workflow.api.domain.usecase

import ru.flowernetes.entity.workflow.Workflow

interface GetAllWorkflowsUseCase {
    fun exec(): List<Workflow>
}