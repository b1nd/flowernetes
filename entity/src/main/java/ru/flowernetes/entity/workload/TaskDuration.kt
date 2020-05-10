package ru.flowernetes.entity.workload

import ru.flowernetes.entity.task.Task

data class TaskDuration(
  val task: Task,
  val durations: List<TimeDurationInfo>,
  val averageTime: Long
)