package ru.flowernetes.scheduling.api.domain.usecase

import ru.flowernetes.entity.task.Task

interface RemoveCronScheduleUseCase {
    fun exec(task: Task)
}