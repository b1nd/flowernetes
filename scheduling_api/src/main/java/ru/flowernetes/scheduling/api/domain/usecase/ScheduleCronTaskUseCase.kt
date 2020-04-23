package ru.flowernetes.scheduling.api.domain.usecase

import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.task.TimeCondition

interface ScheduleCronTaskUseCase {
    fun exec(task: Task, timeCondition: TimeCondition)
}