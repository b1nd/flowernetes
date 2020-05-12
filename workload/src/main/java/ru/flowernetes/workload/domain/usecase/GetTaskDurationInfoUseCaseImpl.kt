package ru.flowernetes.workload.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workload.TaskDuration
import ru.flowernetes.entity.workload.TaskDurationFilter
import ru.flowernetes.entity.workload.TimeDurationInfo
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.workload.api.domain.usecase.GetTaskDurationInfoUseCase
import ru.flowernetes.workload.data.repo.WorkloadRepository
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import kotlin.math.roundToLong

@Component
class GetTaskDurationInfoUseCaseImpl(
  private val workloadRepository: WorkloadRepository
) : GetTaskDurationInfoUseCase {
    override fun exec(task: Task, taskDurationFilter: TaskDurationFilter): TaskDuration? {
        val taskWorkloads = workloadRepository.findAllByTaskAndWorkloadCreationTimeBetween(
          task = task,
          from = taskDurationFilter.from,
          to = taskDurationFilter.to.plusDays(1)
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
        val startTime = workload.taskStartTime ?: workload.workloadCreationTime
        val endTime = workload.taskCompletionTime
          ?: Instant.ofEpochMilli(workload.lastTransitionTime).atZone(ZoneId.systemDefault()).toLocalDateTime()
        return TimeDurationInfo(
          seconds = ChronoUnit.SECONDS.between(startTime, endTime),
          dateTime = startTime
        )
    }
}