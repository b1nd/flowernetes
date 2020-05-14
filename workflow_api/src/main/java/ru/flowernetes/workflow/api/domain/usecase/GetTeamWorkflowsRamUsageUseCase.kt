package ru.flowernetes.workflow.api.domain.usecase

import ru.flowernetes.entity.team.Team
import ru.flowernetes.entity.team.WorkflowsRamUsage
import java.time.LocalDate

interface GetTeamWorkflowsRamUsageUseCase {
    fun exec(team: Team, from: LocalDate, to: LocalDate): List<WorkflowsRamUsage>
}