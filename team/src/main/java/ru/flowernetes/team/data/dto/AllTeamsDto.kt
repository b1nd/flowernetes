package ru.flowernetes.team.data.dto

import ru.flowernetes.entity.team.Team

data class AllTeamsDto(
  val items: List<Team>
)