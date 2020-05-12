package ru.flowernetes.workflow.api.domain.usecase

import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.entity.workload.TasksRamUsage
import java.time.LocalDate

interface GetWorkflowTasksRamUsageUseCase {
    fun exec(workflow: Workflow, from: LocalDate, to: LocalDate): List<TasksRamUsage>
}