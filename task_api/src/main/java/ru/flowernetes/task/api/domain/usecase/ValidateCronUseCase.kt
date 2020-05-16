package ru.flowernetes.task.api.domain.usecase

interface ValidateCronUseCase {
    fun exec(cron: String)
}