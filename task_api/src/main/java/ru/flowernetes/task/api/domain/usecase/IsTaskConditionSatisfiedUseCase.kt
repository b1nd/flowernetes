package ru.flowernetes.task.api.domain.usecase

import ru.flowernetes.entity.task.Task

interface IsTaskConditionSatisfiedUseCase {
    fun exec(task: Task): Boolean
}