package ru.flowernetes.workload.api.domain.dto

import ru.flowernetes.entity.team.Team

data class WorkloadFilter(
  val team: Team? = null
)