package ru.flowernetes.workload.api.domain.dto

import ru.flowernetes.entity.task.TaskStatus
import java.time.LocalDate

data class WorkloadFilter(
  val id: Long? = null,
  val teamId: Long? = null,
  val taskId: Long? = null,
  val fromDate: LocalDate? = null,
  val toDate: LocalDate? = null,
  val taskStatus: TaskStatus? = null
)