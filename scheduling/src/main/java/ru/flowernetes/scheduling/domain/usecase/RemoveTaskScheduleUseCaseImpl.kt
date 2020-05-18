package ru.flowernetes.scheduling.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.scheduling.api.domain.usecase.RemoveCronScheduleUseCase
import ru.flowernetes.scheduling.api.domain.usecase.RemoveTaskScheduleUseCase

@Component
class RemoveTaskScheduleUseCaseImpl(
  private val removeCronScheduleUseCase: RemoveCronScheduleUseCase
) : RemoveTaskScheduleUseCase {

    override fun exec(task: Task) {
        removeCronScheduleUseCase.exec(task)
    }
}