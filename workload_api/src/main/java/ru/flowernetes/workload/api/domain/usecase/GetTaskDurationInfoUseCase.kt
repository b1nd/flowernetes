package ru.flowernetes.workload.api.domain.usecase

import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workload.TaskDuration
import java.time.LocalDate

interface GetTaskDurationInfoUseCase {
    fun exec(task: Task, from: LocalDate, to: LocalDate): TaskDuration?
}