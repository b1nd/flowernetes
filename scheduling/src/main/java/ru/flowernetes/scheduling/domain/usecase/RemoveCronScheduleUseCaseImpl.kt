package ru.flowernetes.scheduling.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.scheduling.api.domain.usecase.RemoveCronScheduleUseCase
import ru.flowernetes.scheduling.data.service.CronService

@Component
class RemoveCronScheduleUseCaseImpl(
  private val cronService: CronService
) : RemoveCronScheduleUseCase {

    override fun exec(task: Task) {
        cronService.removeCronTask(task)
    }
}