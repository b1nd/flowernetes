package ru.flowernetes.workflow.api.domain.usecase

import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workload.TasksCpuUsage
import java.time.LocalDate

interface GetTasksCpuUsageUseCase {
    fun exec(tasks: List<Task>, from: LocalDate, to: LocalDate): List<TasksCpuUsage>
}