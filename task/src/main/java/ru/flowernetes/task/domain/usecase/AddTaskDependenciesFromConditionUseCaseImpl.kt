package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Condition
import ru.flowernetes.entity.task.DependencyMarker
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.task.TaskDependencies
import ru.flowernetes.task.api.domain.usecase.AddTaskDependenciesFromConditionUseCase
import ru.flowernetes.task.api.domain.usecase.GetTaskByIdUseCase
import ru.flowernetes.task.api.domain.usecase.GetTaskIdsFromConditionUseCase
import ru.flowernetes.task.data.repo.DependencyMarkerRepository
import ru.flowernetes.task.data.repo.TaskDependenciesRepository

@Component
class AddTaskDependenciesFromConditionUseCaseImpl(
  private val taskDependenciesRepository: TaskDependenciesRepository,
  private val dependencyMarkerRepository: DependencyMarkerRepository,
  private val getTaskIdsFromConditionUseCase: GetTaskIdsFromConditionUseCase,
  private val getTaskByIdUseCase: GetTaskByIdUseCase
) : AddTaskDependenciesFromConditionUseCase {

    override fun exec(task: Task, condition: Condition): TaskDependencies {
        val tasks = getTaskIdsFromConditionUseCase.exec(condition)
          .map(getTaskByIdUseCase::exec)
        val dependencyMarkers = dependencyMarkerRepository
          .saveAll(tasks.map { DependencyMarker(dependencyTask = it, marker = false) })

        return taskDependenciesRepository.save(TaskDependencies(
          task = task,
          dependencyMarkers = dependencyMarkers
        ))
    }
}