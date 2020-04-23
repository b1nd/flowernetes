package ru.flowernetes.task.api.domain.usecase

import ru.flowernetes.entity.task.Task

interface UserRemoveTaskFromScheduleUseCase {
    fun exec(task: Task)
}