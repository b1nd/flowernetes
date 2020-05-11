package ru.flowernetes.entity.workflow

import ru.flowernetes.entity.workload.TaskTimeInterval

data class GanttChart(
  val intervals: List<TaskTimeInterval>
)