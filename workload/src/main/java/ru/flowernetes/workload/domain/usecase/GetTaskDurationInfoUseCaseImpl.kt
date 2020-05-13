package ru.flowernetes.workload.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workload.TaskDuration
import ru.flowernetes.entity.workload.TimeDurationInfo
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.workload.api.domain.mapper.WorkloadToTimeIntervalMapper
import ru.flowernetes.workload.api.domain.usecase.GetTaskDurationInfoUseCase
import ru.flowernetes.workload.data.repo.WorkloadRepository
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.roundToLong

@Component
class GetTaskDurationInfoUseCaseImpl(
  private val workloadRepository: WorkloadRepository,
  private val workloadToTimeIntervalMapper: WorkloadToTimeIntervalMapper
) : GetTaskDurationInfoUseCase {

    override fun exec(task: Task, from: LocalDate, to: LocalDate): TaskDuration? {
        val taskWorkloads = workloadRepository.findAllByTaskAndWorkloadCreationTimeBetween(
          task = task,
          from = from.atStartOfDay(),
          to = to.plusDays(1).atStartOfDay()
        )
        if (taskWorkloads.isEmpty()) {
            return null
        }
        val durations = taskWorkloads.map(::getTimeDurationInfo).sortedBy { it.dateTime }
        val averageTime = durations.map { it.seconds }.average().roundToLong()

        return TaskDuration(
          task = task,
          durations = durations,
          averageTime = averageTime
        )
    }

    private fun getTimeDurationInfo(workload: Workload): TimeDurationInfo {
        val interval = workloadToTimeIntervalMapper.map(workload)
        return TimeDurationInfo(
          seconds = ChronoUnit.SECONDS.between(interval.from, interval.to),
          dateTime = interval.from
        )
    }
}