package ru.flowernetes.workflow.api.domain.usecase

import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.entity.workload.TimeInterval
import ru.flowernetes.entity.workload.Workload
import java.time.LocalDate

interface GetWorkflowTasksTimeIntervalsToWorkloadsUseCase {
    fun exec(workflow: Workflow, from: LocalDate, to: LocalDate): Map<TimeInterval, List<Workload>>
}