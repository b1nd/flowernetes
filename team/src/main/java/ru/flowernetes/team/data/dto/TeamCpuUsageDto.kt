package ru.flowernetes.team.data.dto

import ru.flowernetes.entity.team.WorkflowsCpuUsage

data class TeamCpuUsageDto(
  val items: List<WorkflowsCpuUsage>
)