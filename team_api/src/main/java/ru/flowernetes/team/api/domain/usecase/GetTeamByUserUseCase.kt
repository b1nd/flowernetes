package ru.flowernetes.team.api.domain.usecase

import ru.flowernetes.entity.auth.User
import ru.flowernetes.entity.team.Team

interface GetTeamByUserUseCase {
    fun exec(user: User): Team
}