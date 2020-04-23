package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.task.api.domain.usecase.UnmarkTaskDependencyMarkersUseCase
import ru.flowernetes.task.data.repo.DependencyMarkerRepository

@Component
class UnmarkTaskDependencyMarkersUseCaseImpl(
  private val dependencyMarkerRepository: DependencyMarkerRepository
) : UnmarkTaskDependencyMarkersUseCase {

    override fun exec(task: Task) {
        dependencyMarkerRepository.saveAll(dependencyMarkerRepository.findAllByTask(task)
          .map { it.copy(marker = false) }
        )
    }
}