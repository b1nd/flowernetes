package ru.flowernetes.task.api.domain.usecase

import ru.flowernetes.entity.task.Task

interface UnmarkTaskDependencyMarkersUseCase {
    fun exec(task: Task)
}