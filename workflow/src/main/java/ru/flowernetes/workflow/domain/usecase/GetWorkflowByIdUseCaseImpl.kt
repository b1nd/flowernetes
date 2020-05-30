package ru.flowernetes.workflow.domain.usecase

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.workflow.api.domain.usecase.GetWorkflowByIdUseCase
import ru.flowernetes.workflow.data.repo.WorkflowRepository
import ru.flowernetes.workflow.api.domain.entity.NoSuchWorkflowException

@Component
class GetWorkflowByIdUseCaseImpl(
  private val workflowRepository: WorkflowRepository
) : GetWorkflowByIdUseCase {

    override fun exec(id: Long): Workflow {
        // todo: check if workflow public or caller is owner
        return workflowRepository.findByIdOrNull(id) ?: throw NoSuchWorkflowException(id)
    }
}