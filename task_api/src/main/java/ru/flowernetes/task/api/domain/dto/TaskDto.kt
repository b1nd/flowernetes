package ru.flowernetes.task.api.domain.dto

import ru.flowernetes.entity.task.Conditions

data class TaskDto(
  val name: String,
  val workflowId: Long,
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