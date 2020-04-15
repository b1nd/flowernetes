package ru.flowernetes.team.api.domain.usecase

import ru.flowernetes.team.api.domain.dto.TeamInfoDto

interface GetAllTeamsInfoUseCase {
    fun exec(): List<TeamInfoDto>
}