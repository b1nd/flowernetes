package ru.flowernetes.team.data.dto

import ru.flowernetes.entity.team.WorkflowsRamUsage

data class TeamRamUsageDto(
  val items: List<WorkflowsRamUsage>
)