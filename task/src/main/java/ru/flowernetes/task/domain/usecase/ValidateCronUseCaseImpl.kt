package ru.flowernetes.task.domain.usecase

import org.springframework.scheduling.support.CronTrigger
import org.springframework.stereotype.Component
import ru.flowernetes.task.api.domain.usecase.ValidateCronUseCase

@Component
class ValidateCronUseCaseImpl : ValidateCronUseCase {
    override fun exec(cron: String) {
        CronTrigger(cron)
    }
}