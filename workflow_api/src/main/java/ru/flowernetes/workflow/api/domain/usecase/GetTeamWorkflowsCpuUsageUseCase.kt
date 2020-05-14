package ru.flowernetes.workflow.api.domain.usecase

import ru.flowernetes.entity.team.Team
import ru.flowernetes.entity.team.WorkflowsCpuUsage
import java.time.LocalDate

interface GetTeamWorkflowsCpuUsageUseCase {
    fun exec(team: Team, from: LocalDate, to: LocalDate): List<WorkflowsCpuUsage>
}