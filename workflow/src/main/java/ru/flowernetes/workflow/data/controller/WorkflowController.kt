package ru.flowernetes.workflow.data.controller

import org.springframework.web.bind.annotation.*
import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.workflow.api.domain.dto.WorkflowDto
import ru.flowernetes.workflow.api.domain.usecase.AddWorkflowUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetAllWorkflowsUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetSessionWorkflowsUseCase
import ru.flowernetes.workflow.data.dto.AllWorkflowsDto

@RestController
@RequestMapping("/workflows")
class WorkflowController(
  private val addWorkflowUseCase: AddWorkflowUseCase,
  private val getSessionWorkflowsUseCase: GetSessionWorkflowsUseCase,
  private val getAllWorkflowsUseCase: GetAllWorkflowsUseCase
) {

    @PutMapping
    fun addWorkflow(@RequestBody workflowDto: WorkflowDto): Workflow {
        return addWorkflowUseCase.exec(workflowDto)
    }

    @GetMapping
    fun getAllWorkflows(): AllWorkflowsDto {
        return AllWorkflowsDto(getAllWorkflowsUseCase.exec())
    }

    @GetMapping("/session")
    fun getSessionWorkflows(): AllWorkflowsDto {
        return AllWorkflowsDto(getSessionWorkflowsUseCase.exec())
    }
}