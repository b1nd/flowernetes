package ru.flowernetes.entity.team

import ru.flowernetes.entity.workflow.WorkflowResourceUsage
import java.time.LocalDateTime

data class WorkflowsResourceUsage<T>(
  val time: LocalDateTime,
  val usages: List<WorkflowResourceUsage<T>>,
  val totalRequest: T,
  val totalLimit: T
)

typealias WorkflowsRamUsage = WorkflowsResourceUsage<Long>
typealias WorkflowsCpuUsage = WorkflowsResourceUsage<Double>