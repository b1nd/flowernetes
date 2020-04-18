package ru.flowernetes.team.api.domain.usecase

import ru.flowernetes.entity.auth.User
import ru.flowernetes.entity.team.Team

interface GetUserByTeamUseCase {
    fun exec(team: Team): User
}