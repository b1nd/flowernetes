package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.auth.api.domain.usecase.GetCallingUserSystemRoleUseCase
import ru.flowernetes.entity.auth.SystemUserRole
import ru.flowernetes.entity.task.Task
import ru.flowernetes.task.api.domain.usecase.GetSessionTasksUseCase
import ru.flowernetes.task.data.repo.TaskRepository
import ru.flowernetes.team.api.domain.usecase.GetCallingUserTeamUseCase

@Component
class GetSessionTasksUseCaseImpl(
  private val getCallingUserSystemRoleUseCase: GetCallingUserSystemRoleUseCase,
  private val getCallingUserTeamUseCase: GetCallingUserTeamUseCase,
  private val taskRepository: TaskRepository
) : GetSessionTasksUseCase {

    override fun exec(): List<Task> {
        return when (getCallingUserSystemRoleUseCase.execute().role) {
            SystemUserRole.ADMIN -> taskRepository.findAll()
            SystemUserRole.TEAM -> {
                val team = getCallingUserTeamUseCase.exec()
                taskRepository.findAllByWorkflow_TeamOrWorkflow_IsPublicIsTrue(team)
            }
        }
    }
}