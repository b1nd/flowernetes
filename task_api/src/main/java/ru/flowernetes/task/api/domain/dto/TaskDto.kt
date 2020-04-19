package ru.flowernetes.task.api.domain.dto

import ru.flowernetes.entity.task.Condition
import ru.flowernetes.entity.workflow.Workflow

data class TaskDto(
  val name: String,
  val workflow: Workflow,
  val condition: Condition,
  val baseImage: String,
  val sourceScriptId: String
)