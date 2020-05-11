package ru.flowernetes.entity.workload

import ru.flowernetes.entity.task.Task

data class TaskTimeInterval(
  val task: Task,
  val interval: TimeInterval
)