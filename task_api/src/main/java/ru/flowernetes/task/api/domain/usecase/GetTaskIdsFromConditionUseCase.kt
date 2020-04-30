package ru.flowernetes.task.api.domain.usecase

import ru.flowernetes.entity.task.LogicCondition

interface GetTaskIdsFromConditionUseCase {
    fun exec(logicCondition: LogicCondition): List<Long>
}