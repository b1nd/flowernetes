package ru.flowernetes.script.api.domain.usecase

import ru.flowernetes.entity.script.SourceScript
import ru.flowernetes.entity.team.Team

interface GetAllTeamSourceScriptsUseCase {
    fun exec(team: Team): List<SourceScript>
}