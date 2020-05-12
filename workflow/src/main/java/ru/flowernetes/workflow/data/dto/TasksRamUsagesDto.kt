package ru.flowernetes.workflow.data.dto

import ru.flowernetes.entity.workload.TasksRamUsage

data class TasksRamUsagesDto(
  val items: List<TasksRamUsage>
)