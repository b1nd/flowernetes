package ru.flowernetes.task.api.domain.usecase

import ru.flowernetes.task.api.domain.dto.TaskDto

interface ValidateTaskUseCase {
    fun exec(taskDto: TaskDto)
}