package ru.flowernetes.workflow.api.domain.usecase

import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.entity.workload.TaskDuration
import java.time.LocalDate

interface GetWorkflowTasksDurationUseCase {
    fun exec(workflow: Workflow, from: LocalDate, to: LocalDate): List<TaskDuration>
}