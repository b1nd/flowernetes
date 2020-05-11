package ru.flowernetes.entity.workload

import java.time.LocalDateTime

data class TimeInterval(
  val from: LocalDateTime,
  val to: LocalDateTime,
  val seconds: Long
)