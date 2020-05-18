package ru.flowernetes.workflow.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.workflow.api.domain.dto.WorkflowDto
import ru.flowernetes.workflow.api.domain.usecase.AddWorkflowUseCase
import ru.flowernetes.workflow.api.domain.usecase.ValidateWorkflowUseCase
import ru.flowernetes.workflow.data.mapper.WorkflowDtoMapper
import ru.flowernetes.workflow.data.repo.WorkflowRepository

@Component
class AddWorkflowUseCaseImpl(
  private val workflowRepository: WorkflowRepository,
  private val validateWorkflowUseCase: ValidateWorkflowUseCase,
  private val workflowDtoMapper: WorkflowDtoMapper
) : AddWorkflowUseCase {

    override fun exec(workflowDto: WorkflowDto): Workflow {
        validateWorkflowUseCase.exec(workflowDto)
        return workflowRepository.save(workflowDtoMapper.map(workflowDto))
    }
}