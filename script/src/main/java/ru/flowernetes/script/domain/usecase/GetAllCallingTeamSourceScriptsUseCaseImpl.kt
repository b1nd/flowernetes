package ru.flowernetes.script.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.script.SourceScript
import ru.flowernetes.script.api.domain.usecase.GetAllCallingTeamSourceScriptsUseCase
import ru.flowernetes.script.api.domain.usecase.GetAllTeamSourceScriptsUseCase
import ru.flowernetes.team.api.domain.usecase.GetCallingUserTeamUseCase

@Component
class GetAllCallingTeamSourceScriptsUseCaseImpl(
  private val getCallingUserTeamUseCase: GetCallingUserTeamUseCase,
  private val getAllTeamSourceScriptsUseCase: GetAllTeamSourceScriptsUseCase
) : GetAllCallingTeamSourceScriptsUseCase {

    override fun exec(): List<SourceScript> {
        val team = getCallingUserTeamUseCase.exec()
        return getAllTeamSourceScriptsUseCase.exec(team)
    }
}