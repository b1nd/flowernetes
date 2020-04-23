package ru.flowernetes.scheduling.api.domain.usecase

import ru.flowernetes.entity.task.Task

interface ScheduleTaskUseCase {
    fun exec(task: Task)
}