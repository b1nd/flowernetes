package ru.flowernetes.workflow.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.task.api.domain.usecase.GetAllTasksByWorkflowUseCase
import ru.flowernetes.task.api.domain.usecase.GetTaskDependenciesUseCase
import ru.flowernetes.workflow.api.domain.dto.WorkflowDto
import ru.flowernetes.workflow.api.domain.entity.WorkflowValidationException
import ru.flowernetes.workflow.api.domain.usecase.GetWorkflowByIdUseCase
import ru.flowernetes.workflow.api.domain.usecase.UpdateWorkflowUseCase
import ru.flowernetes.workflow.data.repo.WorkflowRepository

@Component
class UpdateWorkflowUseCaseImpl(
  private val getWorkflowByIdUseCase: GetWorkflowByIdUseCase,
  private val getAllTasksByWorkflowUseCase: GetAllTasksByWorkflowUseCase,
  private val getTaskDependenciesUseCase: GetTaskDependenciesUseCase,
  private val workflowRepository: WorkflowRepository
) : UpdateWorkflowUseCase {

    override fun exec(workflowId: Long, workflowDto: WorkflowDto): Workflow {
        checkNameNotExists(workflowId, workflowDto)
        val workflow = getWorkflowByIdUseCase.exec(workflowId)
        if (!workflowDto.isPublic) {
            checkWorkflowHasNoDependencies(workflow)
        }
        return workflowRepository.save(workflow.copy(
          name = workflowDto.name,
          isPublic = workflowDto.isPublic,
          team = workflowDto.team
        ))
    }

    private fun checkWorkflowHasNoDependencies(workflow: Workflow) {
        val tasks = getAllTasksByWorkflowUseCase.exec(workflow)
          .flatMap(getTaskDependenciesUseCase::exec)
          .filter { it.workflow != workflow }
        if (tasks.isNotEmpty()) {
            val tasksMsg = tasks.distinct().joinToString { it.workflowWithName() }
            throw IllegalArgumentException("Cannot change workflow visibility because it has dependent tasks: $tasksMsg")
        }
    }

    private fun checkNameNotExists(workflowId: Long, workflowDto: WorkflowDto) {
        workflowRepository.findByName(workflowDto.name)?.let {
            if (it.id != workflowId) {
                throw WorkflowValidationException("Workflow with name ${workflowDto.name} already exists")
            }
        }
    }
}