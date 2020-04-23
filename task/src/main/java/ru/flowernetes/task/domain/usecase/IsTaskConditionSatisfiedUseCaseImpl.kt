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
        val dependencyMarkers = dependencyMarkerRepository.findAllByTask(task)
        val condition = objectMapper.readValue(task.conditionJson, Condition::class.java)
        return isConditionSatisfied(condition, dependencyMarkers)
    }

    private fun isConditionSatisfied(
      condition: Condition,
      dependencyMarkers: List<DependencyMarker>
    ): Boolean = when (condition) {
        is TimeCondition -> false
        is TaskCondition -> dependencyMarkers
          .find { it.dependencyTask.id == condition.taskId }?.marker ?: false
        is AndCondition -> condition.conditions
          .map { isConditionSatisfied(it, dependencyMarkers) }
          .fold(true, { acc, next -> acc && next })
        is OrCondition -> condition.conditions
          .map { isConditionSatisfied(it, dependencyMarkers) }
          .fold(false, { acc, next -> acc || next })
    }
}