package ru.flowernetes.entity.task

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes(
  JsonSubTypes.Type(value = TimeCondition::class, name = "TimeCondition"),
  JsonSubTypes.Type(value = LogicCondition::class, name = "LogicCondition"),
  JsonSubTypes.Type(value = AndCondition::class, name = "AndCondition"),
  JsonSubTypes.Type(value = OrCondition::class, name = "OrCondition"),
  JsonSubTypes.Type(value = TaskCondition::class, name = "TaskCondition")
)
sealed class Condition
sealed class LogicCondition : Condition()

data class TimeCondition(val time: String) : Condition()
data class AndCondition(val logicConditions: List<LogicCondition>) : LogicCondition()
data class OrCondition(val logicConditions: List<LogicCondition>) : LogicCondition()
data class TaskCondition(val taskId: Long) : LogicCondition()

data class Conditions(
  val timeCondition: TimeCondition?,
  val logicCondition: LogicCondition?
)