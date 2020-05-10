package ru.flowernetes.workflow.data.dto

import ru.flowernetes.entity.workload.TaskDuration

data class TasksDurationDto(
  val items: List<TaskDuration>
)