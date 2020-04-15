package ru.flowernetes.team.api.domain.usecase

import ru.flowernetes.entity.team.Team

interface GetCallingUserTeamUseCase {
    fun exec(): Team
}