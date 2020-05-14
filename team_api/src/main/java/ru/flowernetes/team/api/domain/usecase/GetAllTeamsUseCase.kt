package ru.flowernetes.team.api.domain.usecase

import ru.flowernetes.entity.team.Team

interface GetAllTeamsUseCase {
    fun exec(): List<Team>
}