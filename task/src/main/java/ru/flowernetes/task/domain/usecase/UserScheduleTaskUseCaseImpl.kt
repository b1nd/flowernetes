package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.auth.api.domain.usecase.GetCallingUserSystemRoleUseCase
import ru.flowernetes.entity.auth.SystemUserRole
import ru.flowernetes.entity.task.Task
import ru.flowernetes.scheduling.api.domain.usecase.ScheduleTaskUseCase
import ru.flowernetes.task.api.domain.entity.ScheduleTaskNotAllowedException
import ru.flowernetes.task.api.domain.usecase.UserScheduleTaskUseCase
import ru.flowernetes.task.data.repo.TaskRepository
import ru.flowernetes.team.api.domain.usecase.IsCallingUserTeamEqualsTeamUseCase

@Component
class UserScheduleTaskUseCaseImpl(
  private val taskRepository: TaskRepository,
  private val getCallingUserSystemRoleUseCase: GetCallingUserSystemRoleUseCase,
  private val isCallingUserTeamEqualsTeamUseCase: IsCallingUserTeamEqualsTeamUseCase,
  private val scheduleTaskUseCase: ScheduleTaskUseCase
) : UserScheduleTaskUseCase {

    override fun exec(task: Task) {
        if (getCallingUserSystemRoleUseCase.execute().role == SystemUserRole.ADMIN ||
          isCallingUserTeamEqualsTeamUseCase.exec(task.workflow.team)
        ) {
            taskRepository.save(task.copy(scheduled = true)).also {
                scheduleTaskUseCase.exec(it)
            }
        } else {
            throw ScheduleTaskNotAllowedException(task)
        }
    }
}