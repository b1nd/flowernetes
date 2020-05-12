package ru.flowernetes.workflow.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.entity.workload.Interval
import ru.flowernetes.entity.workload.TimeInterval
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.entity.workload.map
import ru.flowernetes.task.api.domain.usecase.GetAllTasksByWorkflowUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetWorkflowTasksTimeIntervalsToWorkloadsUseCase
import ru.flowernetes.workload.api.domain.mapper.WorkloadToTimeIntervalMapper
import ru.flowernetes.workload.api.domain.usecase.GetTaskWorkloadsBetweenDatesUseCase
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

@Component
class GetWorkflowTasksTimeIntervalsToWorkloadsUseCaseImpl(
  private val getAllTasksByWorkflowUseCase: GetAllTasksByWorkflowUseCase,
  private val workloadToTimeIntervalMapper: WorkloadToTimeIntervalMapper,
  private val getTaskWorkloadsBetweenDatesUseCase: GetTaskWorkloadsBetweenDatesUseCase
) : GetWorkflowTasksTimeIntervalsToWorkloadsUseCase {

    override fun exec(workflow: Workflow, from: LocalDate, to: LocalDate): Map<TimeInterval, List<Workload>> {
        val tasks = getAllTasksByWorkflowUseCase.exec(workflow)
        val workloadToIntervalList = tasks
          .flatMap { getTaskWorkloadsBetweenDatesUseCase.exec(it, from, to) }
          .map { it to workloadToTimeIntervalMapper.map(it).map(::timeToMilli) }

        val points = workloadToIntervalList
          .flatMap { listOf(it.second.from, it.second.to) }
          .distinct()
          .sorted()

        val map = newMap(points)

        workloadToIntervalList.forEach { (workload, interval) ->
            addWorkloadToInterval(interval, workload, points, map)
        }

        return map.mapKeys { it.key.map(::milliToTime) }
    }

    private fun addWorkloadToInterval(
      interval: Interval<Long>,
      workload: Workload,
      points: List<Long>,
      map: MutableMap<Interval<Long>, MutableList<Workload>>
    ) {
        val startPointIndex = points.binarySearch(interval.from)
        val subPoints = points.subList(startPointIndex, points.size)
        for (i in 0 until subPoints.size - 1) {
            val subInterval = Interval(subPoints[i], subPoints[i + 1])
            if (overlaps(interval, subInterval)) {
                map[subInterval]?.add(workload)
            } else {
                break
            }
        }
    }

    private fun newMap(points: List<Long>): MutableMap<Interval<Long>, MutableList<Workload>> {
        val map = mutableMapOf<Interval<Long>, MutableList<Workload>>()
        for (i in 0 until points.size - 1) {
            val interval = Interval(points[i], points[i + 1])
            map[interval] = mutableListOf()
        }
        return map
    }

    private fun overlaps(interval: Interval<Long>, subInterval: Interval<Long>): Boolean {
        return interval.from <= subInterval.from && interval.to >= subInterval.to
    }

    private fun timeToMilli(localDateTime: LocalDateTime): Long {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    private fun milliToTime(milli: Long): LocalDateTime {
        return Instant.ofEpochMilli(milli).atZone(ZoneId.systemDefault()).toLocalDateTime()
    }
}