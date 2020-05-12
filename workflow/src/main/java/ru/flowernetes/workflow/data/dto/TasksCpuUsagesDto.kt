package ru.flowernetes.workflow.data.dto

import ru.flowernetes.entity.workload.TasksCpuUsage

data class TasksCpuUsagesDto(
  val items: List<TasksCpuUsage>
)