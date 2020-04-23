package ru.flowernetes.task.api.domain.usecase

import ru.flowernetes.entity.task.Task

interface UserScheduleTaskUseCase {
    fun exec(task: Task)
}