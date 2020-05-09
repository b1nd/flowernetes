package ru.flowernetes.workflow.data.controller

import org.springframework.web.bind.annotation.*
import ru.flowernetes.entity.monitoring.TaskStatusInfo
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workflow.Graph
import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.task.api.domain.usecase.GetAllTasksByWorkflowUseCase
import ru.flowernetes.workflow.api.domain.dto.WorkflowDto
import ru.flowernetes.workflow.api.domain.usecase.*
import ru.flowernetes.workflow.data.dto.AllWorkflowsDto

@RestController
@RequestMapping("/workflows")
class WorkflowController(
  private val addWorkflowUseCase: AddWorkflowUseCase,
  private val getSessionWorkflowsUseCase: GetSessionWorkflowsUseCase,
  private val getAllWorkflowsUseCase: GetAllWorkflowsUseCase,
  private val getWorkflowByIdUseCase: GetWorkflowByIdUseCase,
  private val getAllTasksByWorkflowUseCase: GetAllTasksByWorkflowUseCase,
  private val getAllTasksStatusInfoUseCase: GetAllTasksStatusInfoUseCase,
  private val getWorkflowGraphUseCase: GetWorkflowGraphUseCase
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

    @GetMapping("/{id}/tasks/status")
    fun getAllTasksStatusInfo(@PathVariable id: Long): List<TaskStatusInfo> {
        return getWorkflowByIdUseCase.exec(id).let(getAllTasksStatusInfoUseCase::exec)
    }

    @GetMapping("/{id}/graph")
    fun getWorkflowGraph(@PathVariable id: Long): Graph<Task, Long> {
        return getWorkflowByIdUseCase.exec(id).let(getWorkflowGraphUseCase::exec)
    }
}