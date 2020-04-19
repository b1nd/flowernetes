package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.*
import ru.flowernetes.task.api.domain.usecase.GetTaskIdsFromConditionUseCase

@Component
class GetTaskIdsFromConditionUseCaseImpl : GetTaskIdsFromConditionUseCase {
    override fun exec(condition: Condition): List<Long> = when (condition) {
        is TimeCondition -> listOf()
        is TaskCondition -> listOf(condition.taskId)
        is AndCondition -> condition.conditions.flatMap(::exec)
        is OrCondition -> condition.conditions.flatMap(::exec)
    }
}