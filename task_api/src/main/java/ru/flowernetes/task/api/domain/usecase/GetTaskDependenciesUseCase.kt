package ru.flowernetes.task.api.domain.usecase

import ru.flowernetes.entity.task.Task

interface GetTaskDependenciesUseCase {
    fun exec(task: Task): List<Task>
}