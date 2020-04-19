package ru.flowernetes.workflow.data.controller

import org.springframework.web.bind.annotation.*
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.task.api.domain.usecase.GetAllTasksByWorkflowUseCase
import ru.flowernetes.workflow.api.domain.dto.WorkflowDto
import ru.flowernetes.workflow.api.domain.usecase.AddWorkflowUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetAllWorkflowsUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetSessionWorkflowsUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetWorkflowByIdUseCase
import ru.flowernetes.workflow.data.dto.AllWorkflowsDto

@RestController
@RequestMapping("/workflows")
class WorkflowController(
  private val addWorkflowUseCase: AddWorkflowUseCase,
  private val getSessionWorkflowsUseCase: GetSessionWorkflowsUseCase,
  private val getAllWorkflowsUseCase: GetAllWorkflowsUseCase,
  private val getWorkflowByIdUseCase: GetWorkflowByIdUseCase,
  private val getAllTasksByWorkflowUseCase: GetAllTasksByWorkflowUseCase
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

    @GetMapping("/{id}/tasks")
    fun getAllTasks(@PathVariable id: Long): List<Task> {
        return getWorkflowByIdUseCase.exec(id).let(getAllTasksByWorkflowUseCase::exec)
    }
}