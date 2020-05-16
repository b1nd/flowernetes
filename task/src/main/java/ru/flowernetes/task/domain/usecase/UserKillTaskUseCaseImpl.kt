package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.orchestration.api.domain.usecase.KillTaskUseCase
import ru.flowernetes.task.api.domain.usecase.CheckUserTaskControlAllowedUseCase
import ru.flowernetes.task.api.domain.usecase.UserKillTaskUseCase

@Component
class UserKillTaskUseCaseImpl(
  private val checkUserTaskControlAllowedUseCase: CheckUserTaskControlAllowedUseCase,
  private val killTaskUseCase: KillTaskUseCase
) : UserKillTaskUseCase {

    override fun exec(task: Task) {
        checkUserTaskControlAllowedUseCase.exec(task)
        killTaskUseCase.exec(task)
    }
}