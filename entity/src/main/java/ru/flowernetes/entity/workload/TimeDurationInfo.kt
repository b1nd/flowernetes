package ru.flowernetes.entity.workload

import java.time.LocalDateTime

data class TimeDurationInfo(
  val seconds: Long,
  val dateTime: LocalDateTime
)