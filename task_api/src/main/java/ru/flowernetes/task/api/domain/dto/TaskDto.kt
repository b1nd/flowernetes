package ru.flowernetes.task.api.domain.dto

import ru.flowernetes.entity.task.Conditions
import ru.flowernetes.entity.workflow.Workflow

data class TaskDto(
  val name: String,
  val workflow: Workflow,
  val conditions: Conditions,
  val scheduled: Boolean,
  val baseImage: String,
  val memoryRequest: String,
  val memoryLimit: String,
  val cpuRequest: String,
  val cpuLimit: String,
  val saveLog: Boolean,
  val saveScript: Boolean,
  val sourceScriptId: String
)