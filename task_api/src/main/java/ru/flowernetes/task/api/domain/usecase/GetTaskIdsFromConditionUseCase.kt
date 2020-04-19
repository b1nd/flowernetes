package ru.flowernetes.task.api.domain.usecase

import ru.flowernetes.entity.task.Condition

interface GetTaskIdsFromConditionUseCase {
    fun exec(condition: Condition): List<Long>
}