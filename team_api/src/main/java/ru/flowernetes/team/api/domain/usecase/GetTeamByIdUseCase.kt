package ru.flowernetes.team.api.domain.usecase

import ru.flowernetes.entity.team.Team

interface GetTeamByIdUseCase {
    fun exec(teamId: Long): Team
}