package ru.flowernetes.workflow.api.domain.dto

import ru.flowernetes.entity.team.Team

data class WorkflowDto(
  val name: String,
  val isPublic: Boolean,
  val team: Team
)