package ru.flowernetes.task.api.domain.usecase

import ru.flowernetes.entity.task.Task

interface CheckUserTaskControlAllowedUseCase {
    fun exec(task: Task)
}