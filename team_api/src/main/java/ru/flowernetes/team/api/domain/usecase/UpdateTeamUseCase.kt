package ru.flowernetes.team.api.domain.usecase

import ru.flowernetes.team.api.domain.dto.TeamInfoDto
import ru.flowernetes.team.api.domain.dto.UpdateTeamDto

interface UpdateTeamUseCase {
    fun exec(teamId: Long, updateTeamDto: UpdateTeamDto): TeamInfoDto
}