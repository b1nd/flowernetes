package ru.flowernetes.workload.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workload.TimeInterval
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.workload.api.domain.usecase.GetTaskTimeIntervalsByDateUseCase
import ru.flowernetes.workload.data.repo.WorkloadRepository
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit

@Component
class GetTaskTimeIntervalsByDateUseCaseImpl(
  private val workloadRepository: WorkloadRepository
) : GetTaskTimeIntervalsByDateUseCase {

    override fun exec(task: Task, date: LocalDate): List<TimeInterval> {
        val workloads = workloadRepository.findAllByTaskAndWorkloadCreationTimeBetween(
          task = task,
          from = date.atStartOfDay(),
          to = date.plusDays(1).atStartOfDay()
        )
        return workloads.map(::workloadToTimeInterval)
    }

    private fun workloadToTimeInterval(workload: Workload): TimeInterval {
        val startTime = workload.taskStartTime ?: workload.workloadCreationTime
        val endTime = workload.taskCompletionTime
          ?: Instant.ofEpochMilli(workload.lastTransitionTime).atZone(ZoneId.systemDefault()).toLocalDateTime()
        return TimeInterval(
          from = startTime,
          to = endTime,
          seconds = ChronoUnit.SECONDS.between(startTime, endTime)
        )
    }
}