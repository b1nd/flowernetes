package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.task.api.domain.usecase.GetTaskDependenciesUseCase
import ru.flowernetes.task.data.repo.DependencyMarkerRepository

@Component
class GetTaskDependenciesUseCaseImpl(
  private val dependencyMarkerRepository: DependencyMarkerRepository
) : GetTaskDependenciesUseCase {

    override fun exec(task: Task): List<Task> {
        return dependencyMarkerRepository.findAllByDependencyTask(task).map { it.task }
    }
}