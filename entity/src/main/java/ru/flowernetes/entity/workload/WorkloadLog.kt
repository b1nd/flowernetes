package ru.flowernetes.entity.workload

import java.time.LocalDateTime

data class WorkloadLog(
  val id: String,
  val filename: String,
  val uploadDate: LocalDateTime
)