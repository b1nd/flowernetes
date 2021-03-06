package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.orchestration.api.domain.usecase.RunTaskUseCase
import ru.flowernetes.task.api.domain.usecase.CheckUserTaskControlAllowedUseCase
import ru.flowernetes.task.api.domain.usecase.UserRunTaskUseCase

@Component
class UserRunTaskUseCaseImpl(
  private val checkUserTaskControlAllowedUseCase: CheckUserTaskControlAllowedUseCase,
  private val runTaskUseCase: RunTaskUseCase
) : UserRunTaskUseCase {

    override fun exec(task: Task) {
        checkUserTaskControlAllowedUseCase.exec(task)
        runTaskUseCase.execAsync(task)
    }
}