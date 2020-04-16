package ru.flowernetes.workflow.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.auth.api.domain.usecase.GetCallingUserSystemRoleUseCase
import ru.flowernetes.entity.auth.SystemUserRole
import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.team.api.domain.usecase.GetCallingUserTeamUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetAllWorkflowsUseCase
import ru.flowernetes.workflow.data.repo.WorkflowRepository

@Component
class GetAllWorkflowsUseCaseImpl(
  private val workflowRepository: WorkflowRepository,
  private val getCallingUserTeamUseCase: GetCallingUserTeamUseCase,
  private val getCallingUserSystemRoleUseCase: GetCallingUserSystemRoleUseCase
) : GetAllWorkflowsUseCase {

    override fun exec(): List<Workflow> = when (getCallingUserSystemRoleUseCase.execute().role) {
        SystemUserRole.TEAM -> {
            val sessionTeam = getCallingUserTeamUseCase.exec()
            workflowRepository.findAllByIsPublicIsTrueOrTeam(sessionTeam)
        }
        SystemUserRole.ADMIN -> workflowRepository.findAll()
    }
}