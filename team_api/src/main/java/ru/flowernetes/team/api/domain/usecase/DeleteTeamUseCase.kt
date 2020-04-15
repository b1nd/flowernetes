package ru.flowernetes.team.api.domain.usecase

interface DeleteTeamUseCase {
    fun exec(teamId: Long)
}