package ru.flowernetes.task.domain.usecase

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.task.api.domain.entity.NoSuchTaskException
import ru.flowernetes.task.api.domain.usecase.GetTaskByIdUseCase
import ru.flowernetes.task.data.repo.TaskRepository

@Component
class GetTaskByIdUseCaseImpl(
  private val taskRepository: TaskRepository
) : GetTaskByIdUseCase {

    override fun exec(id: Long): Task {
        return taskRepository.findByIdOrNull(id) ?: throw NoSuchTaskException(id)
    }
}