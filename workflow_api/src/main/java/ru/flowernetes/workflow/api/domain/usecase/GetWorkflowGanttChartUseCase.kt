package ru.flowernetes.workflow.api.domain.usecase

import ru.flowernetes.entity.workflow.GanttChart
import ru.flowernetes.entity.workflow.Workflow
import java.time.LocalDate

interface GetWorkflowGanttChartUseCase {
    fun exec(workflow: Workflow, date: LocalDate): GanttChart
}