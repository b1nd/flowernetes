package ru.flowernetes.workload.api.domain.usecase

import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workload.TimeIntervalWithSeconds
import java.time.LocalDate

interface GetTaskTimeIntervalsWithSecondsByDateUseCase {
    fun exec(task: Task, date: LocalDate): List<TimeIntervalWithSeconds>
}