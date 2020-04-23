package ru.flowernetes.task.api.domain.usecase

import ru.flowernetes.entity.task.Condition
import ru.flowernetes.entity.task.DependencyMarker
import ru.flowernetes.entity.task.Task

interface AddTaskDependenciesFromConditionUseCase {
    fun exec(task: Task, condition: Condition): List<DependencyMarker>
}