package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.task.api.domain.usecase.GetAllScheduledTasksUseCase
import ru.flowernetes.task.data.repo.TaskRepository

@Component
class GetAllScheduledTasksUseCaseImpl(
  private val taskRepository: TaskRepository
) : GetAllScheduledTasksUseCase {

    override fun exec(): List<Task> {
        return taskRepository.findAllByScheduledIsTrue()
    }
}