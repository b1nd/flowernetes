package ru.flowernetes.monitoring.api.domain.usecase

import ru.flowernetes.entity.monitoring.TaskStatusInfo
import ru.flowernetes.entity.task.Task

interface GetTaskStatusInfoUseCase {
    fun exec(task: Task): TaskStatusInfo
}