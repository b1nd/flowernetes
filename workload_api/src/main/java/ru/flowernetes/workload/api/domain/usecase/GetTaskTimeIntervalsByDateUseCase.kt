package ru.flowernetes.workload.api.domain.usecase

import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workload.TimeInterval
import java.time.LocalDate

interface GetTaskTimeIntervalsByDateUseCase {
    fun exec(task: Task, date: LocalDate): List<TimeInterval>
}