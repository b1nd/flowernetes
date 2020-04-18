package ru.flowernetes.script.api.domain.usecase

import ru.flowernetes.entity.script.SourceScript

interface GetAllCallingTeamSourceScriptsUseCase {
    fun exec(): List<SourceScript>
}