package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.task.api.domain.entity.CannotDeleteDependentTaskException
import ru.flowernetes.task.api.domain.usecase.CheckThereAreNoDependenciesOnTaskUseCase
import ru.flowernetes.task.data.repo.DependencyMarkerRepository

@Component
class CheckThereAreNoDependenciesOnTaskUseCaseImpl(
  private val dependencyMarkerRepository: DependencyMarkerRepository
) : CheckThereAreNoDependenciesOnTaskUseCase {

    override fun exec(task: Task) {
        val dependentMarkers = dependencyMarkerRepository.findAllByDependencyTask(task)

        if (dependentMarkers.isNotEmpty()) {
            throw CannotDeleteDependentTaskException(task, dependentMarkers.map { it.task })
        }
    }
}