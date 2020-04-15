package ru.flowernetes.team.api.domain.dto

import ru.flowernetes.auth.api.domain.dto.Credentials

data class TeamDto(
  val name: String,
  val namespace: String,
  val credentials: Credentials
)