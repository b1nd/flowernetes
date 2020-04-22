package ru.flowernetes.team.api.domain.usecase

import ru.flowernetes.entity.team.Team

interface IsCallingUserTeamEqualsTeamUseCase {
    fun exec(team: Team): Boolean
}