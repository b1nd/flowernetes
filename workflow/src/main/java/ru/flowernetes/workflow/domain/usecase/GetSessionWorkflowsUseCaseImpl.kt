package ru.flowernetes.workflow.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.team.api.domain.usecase.GetCallingUserTeamUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetSessionWorkflowsUseCase
import ru.flowernetes.workflow.data.repo.WorkflowRepository

@Component
class GetSessionWorkflowsUseCaseImpl(
  private val workflowRepository: WorkflowRepository,
  private val getCallingUserTeamUseCase: GetCallingUserTeamUseCase
) : GetSessionWorkflowsUseCase {

    override fun exec(): List<Workflow> {
        val sessionTeam = getCallingUserTeamUseCase.exec()
        return workflowRepository.findAllByTeam(sessionTeam)
    }
}