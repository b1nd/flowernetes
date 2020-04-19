package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.task.api.domain.usecase.GetAllTasksByWorkflowUseCase
import ru.flowernetes.task.data.repo.TaskRepository

@Component
class GetAllTasksByWorkflowUseCaseImpl(
  private val taskRepository: TaskRepository
) : GetAllTasksByWorkflowUseCase {

    override fun exec(workflow: Workflow): List<Task> {
        return taskRepository.findAllByWorkflow(workflow)
    }
}