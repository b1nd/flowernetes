package ru.flowernetes.workload.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workload.TimeInterval
import ru.flowernetes.workload.api.domain.mapper.WorkloadToTimeIntervalMapper
import ru.flowernetes.workload.api.domain.usecase.GetTaskTimeIntervalsBetweenDatesUseCase
import ru.flowernetes.workload.data.repo.WorkloadRepository
import java.time.LocalDate

@Component
class GetTaskTimeIntervalsBetweenDatesUseCaseImpl(
  private val workloadRepository: WorkloadRepository,
  private val workloadToTimeIntervalMapper: WorkloadToTimeIntervalMapper
) : GetTaskTimeIntervalsBetweenDatesUseCase {

    override fun exec(task: Task, from: LocalDate, to: LocalDate): List<TimeInterval> {
        val workloads = workloadRepository.findAllByTaskAndWorkloadCreationTimeBetween(
          task = task,
          from = from.atStartOfDay(),
          to = to.plusDays(1).atStartOfDay()
        )
        return workloads.map(workloadToTimeIntervalMapper::map)
    }
}