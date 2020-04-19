package ru.flowernetes.task.domain.usecase

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.flowernetes.task.api.domain.entity.NoSuchTaskException
import ru.flowernetes.task.api.domain.usecase.CheckThereAreNoDependenciesOnTaskUseCase
import ru.flowernetes.task.api.domain.usecase.DeleteTaskByIdUseCase
import ru.flowernetes.task.data.repo.DependencyMarkerRepository
import ru.flowernetes.task.data.repo.TaskDependenciesRepository
import ru.flowernetes.task.data.repo.TaskRepository

@Component
class DeleteTaskByIdUseCaseImpl(
  private val taskRepository: TaskRepository,
  private val taskDependenciesRepository: TaskDependenciesRepository,
  private val dependencyMarkerRepository: DependencyMarkerRepository,
  private val checkThereAreNoDependenciesOnTaskUseCase: CheckThereAreNoDependenciesOnTaskUseCase
) : DeleteTaskByIdUseCase {

    override fun exec(id: Long) {
        val task = taskRepository.findByIdOrNull(id) ?: throw NoSuchTaskException(id)

        checkThereAreNoDependenciesOnTaskUseCase.exec(task)

        val taskDependencies = taskDependenciesRepository.findByTask(task)
        val dependencyMarkersIds = taskDependencies.dependencyMarkers.map { it.id }
        taskDependenciesRepository.delete(taskDependencies)
        dependencyMarkersIds.forEach(dependencyMarkerRepository::deleteById)
        taskRepository.deleteById(id)
    }
}