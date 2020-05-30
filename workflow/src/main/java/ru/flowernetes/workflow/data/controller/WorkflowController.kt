package ru.flowernetes.workflow.data.controller

import org.springframework.web.bind.annotation.*
import ru.flowernetes.entity.monitoring.TaskStatusInfo
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workflow.GanttChart
import ru.flowernetes.entity.workflow.Graph
import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.task.api.domain.usecase.GetAllTasksByWorkflowUseCase
import ru.flowernetes.workflow.api.domain.dto.WorkflowDto
import ru.flowernetes.workflow.api.domain.usecase.*
import ru.flowernetes.workflow.data.dto.AllWorkflowsDto
import ru.flowernetes.workflow.data.dto.TasksCpuUsagesDto
import ru.flowernetes.workflow.data.dto.TasksDurationDto
import ru.flowernetes.workflow.data.dto.TasksRamUsagesDto
import java.time.LocalDate

@RestController
@RequestMapping("/workflows")
class WorkflowController(
  private val addWorkflowUseCase: AddWorkflowUseCase,
  private val updateWorkflowUseCase: UpdateWorkflowUseCase,
  private val deleteWorkflowUseCase: DeleteWorkflowUseCase,
  private val getSessionWorkflowsUseCase: GetSessionWorkflowsUseCase,
  private val getAllWorkflowsUseCase: GetAllWorkflowsUseCase,
  private val getWorkflowByIdUseCase: GetWorkflowByIdUseCase,
  private val getAllTasksByWorkflowUseCase: GetAllTasksByWorkflowUseCase,
  private val getAllTasksStatusInfoUseCase: GetAllTasksStatusInfoUseCase,
  private val getWorkflowGraphUseCase: GetWorkflowGraphUseCase,
  private val getWorkflowGanttChartUseCase: GetWorkflowGanttChartUseCase,
  private val getWorkflowTasksRamUsageUseCase: GetWorkflowTasksRamUsageUseCase,
  private val getWorkflowTasksCpuUsageUseCase: GetWorkflowTasksCpuUsageUseCase,
  private val getWorkflowTasksDurationUseCase: GetWorkflowTasksDurationUseCase
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

    @PatchMapping("/{id}")
    fun updateWorkflow(@PathVariable id: Long, @RequestBody workflowDto: WorkflowDto): Workflow {
        return updateWorkflowUseCase.exec(id, workflowDto)
    }

    @DeleteMapping("/{id}")
    fun deleteWorkflow(@PathVariable id: Long) {
        deleteWorkflowUseCase.exec(id)
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

    @GetMapping("/{id}/duration")
    fun getWorkflowTasksDuration(
      @PathVariable id: Long,
      @RequestParam from: LocalDate = LocalDate.now(),
      @RequestParam to: LocalDate = LocalDate.now()
    ): TasksDurationDto {
        return TasksDurationDto(
          getWorkflowByIdUseCase.exec(id).let { getWorkflowTasksDurationUseCase.exec(it, from, to) }
        )
    }

    @GetMapping("/{id}/gantt")
    fun getWorkflowGanttChart(
      @PathVariable id: Long,
      @RequestParam date: LocalDate = LocalDate.now()
    ): GanttChart {
        return getWorkflowByIdUseCase.exec(id).let { getWorkflowGanttChartUseCase.exec(it, date) }
    }

    @GetMapping("/{id}/ram")
    fun getWorkflowRamUsage(
      @PathVariable id: Long,
      @RequestParam from: LocalDate = LocalDate.now(),
      @RequestParam to: LocalDate = LocalDate.now()
    ): TasksRamUsagesDto {
        return TasksRamUsagesDto(getWorkflowTasksRamUsageUseCase.exec(getWorkflowByIdUseCase.exec(id), from, to))
    }

    @GetMapping("/{id}/cpu")
    fun getWorkflowCpuUsage(
      @PathVariable id: Long,
      @RequestParam from: LocalDate = LocalDate.now(),
      @RequestParam to: LocalDate = LocalDate.now()
    ): TasksCpuUsagesDto {
        return TasksCpuUsagesDto(getWorkflowTasksCpuUsageUseCase.exec(getWorkflowByIdUseCase.exec(id), from, to))
    }
}