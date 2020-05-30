package ru.flowernetes.workflow.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.task.api.domain.usecase.GetAllTasksByWorkflowUseCase
import ru.flowernetes.workflow.api.domain.usecase.DeleteWorkflowUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetWorkflowByIdUseCase
import ru.flowernetes.workflow.data.repo.WorkflowRepository

@Component
class DeleteWorkflowUseCaseImpl(
  private val workflowRepository: WorkflowRepository,
  private val getWorkflowByIdUseCase: GetWorkflowByIdUseCase,
  private val getAllTasksByWorkflowUseCase: GetAllTasksByWorkflowUseCase
) : DeleteWorkflowUseCase {

    override fun exec(workflowId: Long) {
        val workflow = getWorkflowByIdUseCase.exec(workflowId)
        checkWorkflowHasNoTasks(workflow)
        workflowRepository.delete(workflow)
    }

    private fun checkWorkflowHasNoTasks(workflow: Workflow) {
        val workflowTasks = getAllTasksByWorkflowUseCase.exec(workflow)
        if (workflowTasks.isNotEmpty()) {
            throw IllegalStateException("Cannot delete workflow: Workflow ${workflow.name} contains tasks!")
        }
    }
}