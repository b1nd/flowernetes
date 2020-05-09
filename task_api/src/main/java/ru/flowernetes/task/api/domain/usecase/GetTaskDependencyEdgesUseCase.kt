package ru.flowernetes.task.api.domain.usecase

import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workflow.Edge

interface GetTaskDependencyEdgesUseCase {
    fun exec(task: Task): List<Edge<Long>>
}