package ru.flowernetes.workflow.api.domain.usecase

import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workload.TasksRamUsage
import java.time.LocalDate

interface GetTasksRamUsageUseCase {
    fun exec(tasks: List<Task>, from: LocalDate, to: LocalDate): List<TasksRamUsage>
}