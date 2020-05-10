package ru.flowernetes.entity.workload

import java.time.LocalDateTime

data class TaskDurationFilter(
  val from: LocalDateTime,
  val to: LocalDateTime
)