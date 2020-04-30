package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.DependencyMarker
import ru.flowernetes.entity.task.LogicCondition
import ru.flowernetes.entity.task.Task
import ru.flowernetes.task.api.domain.usecase.AddTaskDependenciesFromLogicConditionUseCase
import ru.flowernetes.task.api.domain.usecase.GetTaskByIdUseCase
import ru.flowernetes.task.api.domain.usecase.GetTaskIdsFromConditionUseCase
import ru.flowernetes.task.data.repo.DependencyMarkerRepository

@Component
class AddTaskDependenciesFromLogicConditionUseCaseImpl(
  private val dependencyMarkerRepository: DependencyMarkerRepository,
  private val getTaskIdsFromConditionUseCase: GetTaskIdsFromConditionUseCase,
  private val getTaskByIdUseCase: GetTaskByIdUseCase
) : AddTaskDependenciesFromLogicConditionUseCase {

    override fun exec(task: Task, logicCondition: LogicCondition): List<DependencyMarker> {
        val tasks = getTaskIdsFromConditionUseCase.exec(logicCondition)
          .map(getTaskByIdUseCase::exec)

        return dependencyMarkerRepository
          .saveAll(tasks.map { DependencyMarker(task = task, dependencyTask = it, marker = false) })
    }
}