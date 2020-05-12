package ru.flowernetes.workflow.api.domain.usecase

import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.entity.workload.TasksCpuUsage
import java.time.LocalDate

interface GetWorkflowTasksCpuUsageUseCase {
    fun exec(workflow: Workflow, from: LocalDate, to: LocalDate): List<TasksCpuUsage>
}