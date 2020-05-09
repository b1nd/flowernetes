package ru.flowernetes.task.domain.usecase

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.*
import ru.flowernetes.entity.workflow.Edge
import ru.flowernetes.entity.workflow.EdgeType
import ru.flowernetes.task.api.domain.usecase.GetTaskDependencyEdgesUseCase

@Component
class GetTaskDependencyEdgesUseCaseImpl(
  private val objectMapper: ObjectMapper
) : GetTaskDependencyEdgesUseCase {

    override fun exec(task: Task): List<Edge<Long>> {
        val conditions = objectMapper.readValue(task.conditionsJson, Conditions::class.java)
        return conditions.logicCondition?.let { getEdges(task, it) } ?: listOf()
    }

    private fun getEdges(task: Task, logicCondition: LogicCondition, edgeType: EdgeType = EdgeType.STRICT): List<Edge<Long>> = when (logicCondition) {
        is TaskCondition -> listOf(Edge(logicCondition.taskId, task.id, edgeType))
        is AndCondition -> logicCondition.logicConditions.flatMap { getEdges(task, it, EdgeType.STRICT) }
        is OrCondition -> logicCondition.logicConditions.flatMap { getEdges(task, it, EdgeType.WEAK) }
    }
}