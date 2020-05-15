package ru.flowernetes.task.api.domain.usecase

import ru.flowernetes.entity.task.Task
import ru.flowernetes.task.api.domain.dto.TaskDto

interface UpdateTaskUseCase {
    fun exec(taskId: Long, taskDto: TaskDto): Task
}