package ru.flowernetes.workload.api.domain.model

import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.task.TaskStatus

data class WorkloadModel(
  val task: Task,
  val taskStatus: TaskStatus,
  val jobName: String,
  val baseImage: String,
  val memoryRequest: Long,
  val memoryLimit: Long,
  val cpuRequest: Double,
  val cpuLimit: Double,
  val sourceScriptId: String
)