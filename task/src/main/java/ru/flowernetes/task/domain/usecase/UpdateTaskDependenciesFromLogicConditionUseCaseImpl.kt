package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.DependencyMarker
import ru.flowernetes.entity.task.LogicCondition
import ru.flowernetes.entity.task.Task
import ru.flowernetes.task.api.domain.usecase.GetTaskByIdUseCase
import ru.flowernetes.task.api.domain.usecase.GetTaskIdsFromConditionUseCase
import ru.flowernetes.task.api.domain.usecase.UpdateTaskDependenciesFromLogicConditionUseCase
import ru.flowernetes.task.data.repo.DependencyMarkerRepository

@Component
class UpdateTaskDependenciesFromLogicConditionUseCaseImpl(
  private val dependencyMarkerRepository: DependencyMarkerRepository,
  private val getTaskIdsFromConditionUseCase: GetTaskIdsFromConditionUseCase,
  private val getTaskByIdUseCase: GetTaskByIdUseCase
) : UpdateTaskDependenciesFromLogicConditionUseCase {

    override fun exec(task: Task, logicCondition: LogicCondition?): List<DependencyMarker> {
        val taskIds = logicCondition?.let { getTaskIdsFromConditionUseCase.exec(it) } ?: listOf()
        val tasks = taskIds
          .map(getTaskByIdUseCase::exec)
          .toSet()
        val oldDependencyMarkers = dependencyMarkerRepository.findAllByTask(task)
        val sameTasks = oldDependencyMarkers
          .filter { tasks.contains(it.dependencyTask) }
          .map { it.dependencyTask }
          .toSet()
        val deletedDependencyMarkers = oldDependencyMarkers
          .filter { !tasks.contains(it.dependencyTask) }
        val newTasks = tasks.filter { !sameTasks.contains(it) }
        val newDependencyMarkers = newTasks
          .map { DependencyMarker(task = task, dependencyTask = it, marker = false) }

        dependencyMarkerRepository.deleteAll(deletedDependencyMarkers)
        return dependencyMarkerRepository.saveAll(newDependencyMarkers)
    }
}