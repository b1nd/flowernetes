package ru.flowernetes.scheduling.api.domain.usecase

import ru.flowernetes.entity.task.Task

interface RemoveTaskScheduleUseCase {
    fun exec(task: Task)
}