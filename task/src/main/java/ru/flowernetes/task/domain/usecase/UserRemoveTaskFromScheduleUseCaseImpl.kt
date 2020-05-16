package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.task.api.domain.usecase.CheckUserTaskControlAllowedUseCase
import ru.flowernetes.task.api.domain.usecase.RemoveTaskFromScheduleUseCase
import ru.flowernetes.task.api.domain.usecase.UserRemoveTaskFromScheduleUseCase

@Component
class UserRemoveTaskFromScheduleUseCaseImpl(
  private val checkUserTaskControlAllowedUseCase: CheckUserTaskControlAllowedUseCase,
  private val removeTaskFromScheduleUseCase: RemoveTaskFromScheduleUseCase
) : UserRemoveTaskFromScheduleUseCase {

    override fun exec(task: Task) {
        checkUserTaskControlAllowedUseCase.exec(task)
        removeTaskFromScheduleUseCase.exec(task)
    }
}