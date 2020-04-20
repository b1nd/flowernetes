package ru.flowernetes.task.api.domain.dto

import ru.flowernetes.entity.task.Condition
import ru.flowernetes.entity.workflow.Workflow

data class TaskDto(
  val name: String,
  val workflow: Workflow,
  val condition: Condition,
  val baseImage: String,
  val memoryRequest: String,
  val memoryLimit: String,
  val cpuRequest: String,
  val cpuLimit: String,
  val sourceScriptId: String
)