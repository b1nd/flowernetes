package ru.flowernetes.workflow.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.workflow.api.domain.dto.WorkflowDto
import ru.flowernetes.workflow.api.domain.entity.WorkflowValidationException
import ru.flowernetes.workflow.api.domain.usecase.ValidateWorkflowUseCase
import ru.flowernetes.workflow.data.repo.WorkflowRepository

@Component
class ValidateWorkflowUseCaseImpl(
  private val workflowRepository: WorkflowRepository
) : ValidateWorkflowUseCase {

    override fun exec(workflowDto: WorkflowDto) {
        checkNameIsUnique(workflowDto)
    }

    private fun checkNameIsUnique(workflowDto: WorkflowDto) {
        workflowRepository.findByName(workflowDto.name)?.let {
            throw WorkflowValidationException("Workflow with name ${workflowDto.name} already exists")
        }
    }
}