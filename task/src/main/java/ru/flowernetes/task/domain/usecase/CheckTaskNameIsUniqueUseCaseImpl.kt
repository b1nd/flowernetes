package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.task.api.domain.entity.TaskValidationException
import ru.flowernetes.task.api.domain.usecase.CheckTaskNameIsUniqueUseCase
import ru.flowernetes.task.data.repo.TaskRepository

@Component
class CheckTaskNameIsUniqueUseCaseImpl(
  private val taskRepository: TaskRepository
) : CheckTaskNameIsUniqueUseCase {

    override fun exec(task: Task) {
        taskRepository.findByWorkflowAndName(task.workflow, task.name)?.let {
            if (task.id != it.id) {
                throw TaskValidationException("Task with name ${task.name} already exists in workflow ${task.workflow.name}")
            }
        }
    }
}