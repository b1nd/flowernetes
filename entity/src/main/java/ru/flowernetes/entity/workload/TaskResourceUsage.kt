package ru.flowernetes.entity.workload

import ru.flowernetes.entity.task.Task

data class TaskResourceUsage<T>(
  val task: Task,
  val request: T,
  val limit: T
)