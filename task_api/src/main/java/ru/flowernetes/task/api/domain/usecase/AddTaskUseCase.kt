package ru.flowernetes.task.api.domain.usecase

import ru.flowernetes.entity.task.Task
import ru.flowernetes.task.api.domain.dto.TaskDto

interface AddTaskUseCase {
    fun exec(taskDto: TaskDto): Task
}