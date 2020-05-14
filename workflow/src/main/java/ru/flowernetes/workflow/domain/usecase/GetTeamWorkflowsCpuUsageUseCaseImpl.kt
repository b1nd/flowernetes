package ru.flowernetes.workflow.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.team.Team
import ru.flowernetes.entity.team.WorkflowsCpuUsage
import ru.flowernetes.task.api.domain.usecase.GetAllTasksByWorkflowUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetTasksCpuUsageUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetTeamWorkflowsCpuUsageUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetWorkflowsResourceUsageUseCase
import ru.flowernetes.workflow.data.repo.WorkflowRepository
import java.time.LocalDate

@Component
class GetTeamWorkflowsCpuUsageUseCaseImpl(
  private val workflowRepository: WorkflowRepository,
  private val getAllTasksByWorkflowUseCase: GetAllTasksByWorkflowUseCase,
  private val getWorkflowsResourceUsageUseCase: GetWorkflowsResourceUsageUseCase,
  private val getTasksCpuUsageUseCase: GetTasksCpuUsageUseCase
) : GetTeamWorkflowsCpuUsageUseCase {

    override fun exec(team: Team, from: LocalDate, to: LocalDate): List<WorkflowsCpuUsage> {
        val teamWorkflows = workflowRepository.findAllByTeam(team)
        val teamTasks = teamWorkflows.flatMap(getAllTasksByWorkflowUseCase::exec)
        val tasksRamUsages = getTasksCpuUsageUseCase.exec(teamTasks, from, to)
        return getWorkflowsResourceUsageUseCase.exec(
          tasksRamUsages,
          { a, b -> a + b },
          0.0
        )
    }
}