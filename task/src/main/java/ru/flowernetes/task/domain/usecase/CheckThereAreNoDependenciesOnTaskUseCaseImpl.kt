package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.task.api.domain.entity.CannotDeleteDependentTask
import ru.flowernetes.task.api.domain.usecase.CheckThereAreNoDependenciesOnTaskUseCase
import ru.flowernetes.task.data.repo.TaskDependenciesRepository

@Component
class CheckThereAreNoDependenciesOnTaskUseCaseImpl(
  private val taskDependenciesRepository: TaskDependenciesRepository
) : CheckThereAreNoDependenciesOnTaskUseCase {

    override fun exec(task: Task) {
        val dependentTasks = taskDependenciesRepository.findAll()
          .flatMap { dependencies -> dependencies.dependencyMarkers.map { Pair(dependencies, it.dependencyTask) } }
          .filter { it.second == task }
          .map { it.second }

        if (dependentTasks.isNotEmpty()) {
            throw CannotDeleteDependentTask(task, dependentTasks)
        }
    }
}