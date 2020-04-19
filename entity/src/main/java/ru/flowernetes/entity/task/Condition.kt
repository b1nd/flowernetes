package ru.flowernetes.entity.task

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes(
  JsonSubTypes.Type(value = TimeCondition::class, name = "TimeCondition"),
  JsonSubTypes.Type(value = AndCondition::class, name = "AndCondition"),
  JsonSubTypes.Type(value = OrCondition::class, name = "OrCondition"),
  JsonSubTypes.Type(value = TaskCondition::class, name = "TaskCondition")
)
sealed class Condition
data class TimeCondition(val time: String) : Condition()
data class AndCondition(val conditions: List<Condition>) : Condition()
data class OrCondition(val conditions: List<Condition>) : Condition()
data class TaskCondition(val taskId: Long) : Condition()