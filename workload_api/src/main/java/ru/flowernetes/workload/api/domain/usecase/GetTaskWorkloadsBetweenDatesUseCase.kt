package ru.flowernetes.workload.api.domain.usecase

import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workload.Workload
import java.time.LocalDate

interface GetTaskWorkloadsBetweenDatesUseCase {
    fun exec(task: Task, from: LocalDate, to: LocalDate): List<Workload>
}