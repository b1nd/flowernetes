package ru.flowernetes.scheduling.domain.usecase

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.task.TimeCondition
import ru.flowernetes.scheduling.api.domain.usecase.ScheduleCronTaskUseCase
import ru.flowernetes.scheduling.data.service.CronService

@Component
class ScheduleCronTaskUseCaseImpl(
  private val cronService: CronService
) : ScheduleCronTaskUseCase {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun exec(task: Task, timeCondition: TimeCondition) {
        cronService.addCronTask(task, timeCondition.time)
        log.info("Scheduled $task by cron: ${timeCondition.time}")
    }
}