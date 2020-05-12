package ru.flowernetes.workflow.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.workflow.GanttChart
import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.entity.workload.TaskTimeInterval
import ru.flowernetes.task.api.domain.usecase.GetAllTasksByWorkflowUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetWorkflowGanttChartUseCase
import ru.flowernetes.workload.api.domain.usecase.GetTaskTimeIntervalsWithSecondsByDateUseCase
import java.time.LocalDate

@Component
class GetWorkflowGanttChartUseCaseImpl(
  private val getAllTasksByWorkflowUseCase: GetAllTasksByWorkflowUseCase,
  private val getTaskTimeIntervalsWithSecondsByDateUseCase: GetTaskTimeIntervalsWithSecondsByDateUseCase
) : GetWorkflowGanttChartUseCase {

    override fun exec(workflow: Workflow, date: LocalDate): GanttChart {
        val tasks = getAllTasksByWorkflowUseCase.exec(workflow)
        return GanttChart(
          intervals = tasks.flatMap { task ->
              getTaskTimeIntervalsWithSecondsByDateUseCase.exec(task, date).map {
                  TaskTimeInterval(task, it)
              }
          }.sortedBy { it.interval.from }
        )
    }
}