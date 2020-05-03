package ru.flowernetes.entity.workload

import java.time.LocalDateTime

data class WorkloadOutputFile(
  val id: String,
  val filename: String,
  val uploadDate: LocalDateTime
)