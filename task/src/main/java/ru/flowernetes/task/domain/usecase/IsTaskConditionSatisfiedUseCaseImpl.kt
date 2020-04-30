package ru.flowernetes.task.domain.usecase

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.*
import ru.flowernetes.task.api.domain.usecase.IsTaskConditionSatisfiedUseCase
import ru.flowernetes.task.data.repo.DependencyMarkerRepository

@Component
class IsTaskConditionSatisfiedUseCaseImpl(
  private val objectMapper: ObjectMapper,
  private val dependencyMarkerRepository: DependencyMarkerRepository
) : IsTaskConditionSatisfiedUseCase {

    override fun exec(task: Task): Boolean {
        val conditions = objectMapper.readValue(task.conditionsJson, Conditions::class.java)

        val logicCondition = conditions.logicCondition?.let {
            val dependencyMarkers = dependencyMarkerRepository.findAllByTask(task)
            isLogicConditionSatisfied(it, dependencyMarkers)
        }

        return logicCondition ?: false
    }

    private fun isLogicConditionSatisfied(
      logicCondition: LogicCondition,
      dependencyMarkers: List<DependencyMarker>
    ): Boolean = when (logicCondition) {
        is TaskCondition -> dependencyMarkers
          .find { it.dependencyTask.id == logicCondition.taskId }?.marker ?: false
        is AndCondition -> logicCondition.logicConditions
          .map { isLogicConditionSatisfied(it, dependencyMarkers) }
          .fold(true, { acc, next -> acc && next })
        is OrCondition -> logicCondition.logicConditions
          .map { isLogicConditionSatisfied(it, dependencyMarkers) }
          .fold(false, { acc, next -> acc || next })
    }
}