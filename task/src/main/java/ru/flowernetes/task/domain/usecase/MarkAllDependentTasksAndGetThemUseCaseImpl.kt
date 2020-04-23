package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.task.api.domain.usecase.MarkAllDependentTasksAndGetThemUseCase
import ru.flowernetes.task.data.repo.DependencyMarkerRepository

@Component
class MarkAllDependentTasksAndGetThemUseCaseImpl(
  private val dependencyMarkerRepository: DependencyMarkerRepository
) : MarkAllDependentTasksAndGetThemUseCase {

    override fun exec(task: Task): List<Task> {
        val dependencyMarkers = dependencyMarkerRepository
          .findAllByDependencyTaskAndTask_ScheduledIsTrue(task)
        dependencyMarkerRepository.saveAll(dependencyMarkers.map { it.copy(marker = true) })

        return dependencyMarkers.map { it.task }
    }
}