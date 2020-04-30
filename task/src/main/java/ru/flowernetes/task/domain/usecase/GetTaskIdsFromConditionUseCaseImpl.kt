package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.*
import ru.flowernetes.task.api.domain.usecase.GetTaskIdsFromConditionUseCase

@Component
class GetTaskIdsFromConditionUseCaseImpl : GetTaskIdsFromConditionUseCase {
    override fun exec(logicCondition: LogicCondition): List<Long> = when (logicCondition) {
        is TaskCondition -> listOf(logicCondition.taskId)
        is AndCondition -> logicCondition.logicConditions.flatMap(::exec)
        is OrCondition -> logicCondition.logicConditions.flatMap(::exec)
    }
}