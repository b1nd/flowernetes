package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.auth.api.domain.usecase.GetCallingUserSystemRoleUseCase
import ru.flowernetes.entity.auth.SystemUserRole
import ru.flowernetes.entity.task.Task
import ru.flowernetes.scheduling.api.domain.usecase.RemoveTaskFromScheduleUseCase
import ru.flowernetes.task.api.domain.entity.ScheduleTaskNotAllowedException
import ru.flowernetes.task.api.domain.usecase.UserRemoveTaskFromScheduleUseCase
import ru.flowernetes.task.data.repo.TaskRepository
import ru.flowernetes.team.api.domain.usecase.IsCallingUserTeamEqualsTeamUseCase

@Component
class UserRemoveTaskFromScheduleUseCaseImpl(
  private val removeTaskFromScheduleUseCase: RemoveTaskFromScheduleUseCase,
  private val getCallingUserSystemRoleUseCase: GetCallingUserSystemRoleUseCase,
  private val isCallingUserTeamEqualsTeamUseCase: IsCallingUserTeamEqualsTeamUseCase,
  private val taskRepository: TaskRepository
) : UserRemoveTaskFromScheduleUseCase {

    override fun exec(task: Task) {
        if (getCallingUserSystemRoleUseCase.execute().role == SystemUserRole.ADMIN ||
          isCallingUserTeamEqualsTeamUseCase.exec(task.workflow.team)
        ) {
            taskRepository.save(task.copy(scheduled = false)).also {
                removeTaskFromScheduleUseCase.exec(task)
            }
        } else {
            throw ScheduleTaskNotAllowedException(task)
        }
    }
}