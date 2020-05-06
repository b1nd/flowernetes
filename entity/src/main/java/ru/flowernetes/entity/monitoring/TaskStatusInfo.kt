package ru.flowernetes.entity.monitoring

import ru.flowernetes.entity.task.TaskStatus

data class TaskStatusInfo(
  val taskId: Long,
  val taskStatus: TaskStatus,
  val lastTransitionTime: Long
)