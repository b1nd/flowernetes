package ru.flowernetes.entity.workload

import java.time.LocalDateTime

data class TimeIntervalWithSeconds(
  val from: LocalDateTime,
  val to: LocalDateTime,
  val seconds: Long
)