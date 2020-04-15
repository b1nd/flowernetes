package ru.flowernetes.team.data.dto

import ru.flowernetes.team.api.domain.dto.TeamInfoDto

data class AllTeamsInfoDto(
  val items: List<TeamInfoDto>
)