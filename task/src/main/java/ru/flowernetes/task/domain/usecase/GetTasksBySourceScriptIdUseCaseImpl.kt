package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.task.api.domain.usecase.GetTasksBySourceScriptIdUseCase
import ru.flowernetes.task.data.repo.TaskRepository

@Component
class GetTasksBySourceScriptIdUseCaseImpl(
  private val taskRepository: TaskRepository
) : GetTasksBySourceScriptIdUseCase {

    override fun exec(sourceScriptId: String): List<Task> {
        return taskRepository.findAllBySourceScriptId(sourceScriptId)
    }
}