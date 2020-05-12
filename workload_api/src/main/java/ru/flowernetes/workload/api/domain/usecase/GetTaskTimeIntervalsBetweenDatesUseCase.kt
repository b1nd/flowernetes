package ru.flowernetes.workload.api.domain.usecase

import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workload.TimeInterval
import java.time.LocalDate

interface GetTaskTimeIntervalsBetweenDatesUseCase {
    fun exec(task: Task, from: LocalDate, to: LocalDate): List<TimeInterval>
}