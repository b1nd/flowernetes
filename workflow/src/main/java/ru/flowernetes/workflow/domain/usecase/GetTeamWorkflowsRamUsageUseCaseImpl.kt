package ru.flowernetes.workflow.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.team.Team
import ru.flowernetes.entity.team.WorkflowsRamUsage
import ru.flowernetes.task.api.domain.usecase.GetAllTasksByWorkflowUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetTasksRamUsageUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetTeamWorkflowsRamUsageUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetWorkflowsResourceUsageUseCase
import ru.flowernetes.workflow.data.repo.WorkflowRepository
import java.time.LocalDate

@Component
class GetTeamWorkflowsRamUsageUseCaseImpl(
  private val workflowRepository: WorkflowRepository,
  private val getAllTasksByWorkflowUseCase: GetAllTasksByWorkflowUseCase,
  private val getWorkflowsResourceUsageUseCase: GetWorkflowsResourceUsageUseCase,
  private val getTasksRamUsageUseCase: GetTasksRamUsageUseCase
) : GetTeamWorkflowsRamUsageUseCase {

    override fun exec(team: Team, from: LocalDate, to: LocalDate): List<WorkflowsRamUsage> {
        val teamWorkflows = workflowRepository.findAllByTeam(team)
        val teamTasks = teamWorkflows.flatMap(getAllTasksByWorkflowUseCase::exec)
        val tasksRamUsages = getTasksRamUsageUseCase.exec(teamTasks, from, to)
        return getWorkflowsResourceUsageUseCase.exec(
          tasksRamUsages,
          { a, b -> a + b },
          0L
        )
    }
}