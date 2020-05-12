package ru.flowernetes.entity.workload

import java.time.LocalDateTime

data class TasksResourceUsage<T, R>(
  val time: LocalDateTime,
  val usages: List<TaskResourceUsage<T>>,
  val totalRequest: R,
  val totalLimit: R
)

typealias TasksRamUsage = TasksResourceUsage<Long, Long>
typealias TasksCpuUsage = TasksResourceUsage<Double, Double>