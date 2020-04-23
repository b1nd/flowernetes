package ru.flowernetes.scheduling.api.domain.usecase

import ru.flowernetes.entity.task.Task

interface RemoveTaskFromScheduleUseCase {
    fun exec(task: Task)
}