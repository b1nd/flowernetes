package ru.flowernetes.task.api.domain.usecase

import ru.flowernetes.entity.task.Condition
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.task.TaskDependencies

interface AddTaskDependenciesFromConditionUseCase {
    fun exec(task: Task, condition: Condition): TaskDependencies
}