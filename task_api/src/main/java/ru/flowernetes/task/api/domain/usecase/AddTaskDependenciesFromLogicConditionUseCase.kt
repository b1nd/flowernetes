package ru.flowernetes.task.api.domain.usecase

import ru.flowernetes.entity.task.DependencyMarker
import ru.flowernetes.entity.task.LogicCondition
import ru.flowernetes.entity.task.Task

interface AddTaskDependenciesFromLogicConditionUseCase {
    fun exec(task: Task, logicCondition: LogicCondition): List<DependencyMarker>
}