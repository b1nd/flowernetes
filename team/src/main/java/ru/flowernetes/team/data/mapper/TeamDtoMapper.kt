package ru.flowernetes.team.data.mapper

import org.springframework.stereotype.Component
import ru.flowernetes.entity.team.Team
import ru.flowernetes.team.api.domain.dto.TeamDto
import ru.flowernetes.util.mapper.Mapper

@Component
class TeamDtoMapper : Mapper<TeamDto, Team> {
    override fun map(it: TeamDto): Team = Team(
      name = it.name,
      namespace = it.namespace
    )
}