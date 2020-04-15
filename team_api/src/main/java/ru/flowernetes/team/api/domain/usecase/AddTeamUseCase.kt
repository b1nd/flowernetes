package ru.flowernetes.team.api.domain.usecase

import ru.flowernetes.team.api.domain.dto.TeamDto
import ru.flowernetes.team.api.domain.dto.TeamInfoDto

interface AddTeamUseCase {
    fun exec(teamDto: TeamDto): TeamInfoDto
}