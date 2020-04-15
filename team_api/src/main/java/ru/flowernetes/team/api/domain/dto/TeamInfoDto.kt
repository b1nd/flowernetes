package ru.flowernetes.team.api.domain.dto

import ru.flowernetes.entity.orchestration.Namespace

data class TeamInfoDto(
  val id: Long,
  val name: String,
  val namespace: Namespace
)