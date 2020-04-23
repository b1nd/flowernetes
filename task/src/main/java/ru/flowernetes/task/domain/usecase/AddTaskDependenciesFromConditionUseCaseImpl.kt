package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Condition
import ru.flowernetes.entity.task.DependencyMarker
import ru.flowernetes.entity.task.Task
import ru.flowernetes.task.api.domain.usecase.AddTaskDependenciesFromConditionUseCase
import ru.flowernetes.task.api.domain.usecase.GetTaskByIdUseCase
import ru.flowernetes.task.api.domain.usecase.GetTaskIdsFromConditionUseCase
import ru.flowernetes.task.data.repo.DependencyMarkerRepository

@Component
class AddTaskDependenciesFromConditionUseCaseImpl(
  private val dependencyMarkerRepository: DependencyMarkerRepository,
  private val getTaskIdsFromConditionUseCase: GetTaskIdsFromConditionUseCase,
  private val getTaskByIdUseCase: GetTaskByIdUseCase
) : AddTaskDependenciesFromConditionUseCase {

    override fun exec(task: Task, condition: Condition): List<DependencyMarker> {
        val tasks = getTaskIdsFromConditionUseCase.exec(condition)
          .map(getTaskByIdUseCase::exec)

        return dependencyMarkerRepository
          .saveAll(tasks.map { DependencyMarker(task = task, dependencyTask = it, marker = false) })
    }
}