package ru.flowernetes.workflow.api.domain.usecase

import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workload.TimeInterval
import ru.flowernetes.entity.workload.Workload
import java.time.LocalDate

interface GetTasksTimeIntervalsToWorkloadsUseCase {
    fun exec(tasks: List<Task>, from: LocalDate, to: LocalDate): Map<TimeInterval, List<Workload>>
}