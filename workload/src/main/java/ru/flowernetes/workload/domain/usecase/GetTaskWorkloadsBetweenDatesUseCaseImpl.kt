package ru.flowernetes.workload.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.workload.api.domain.usecase.GetTaskWorkloadsBetweenDatesUseCase
import ru.flowernetes.workload.data.repo.WorkloadRepository
import java.time.LocalDate

@Component
class GetTaskWorkloadsBetweenDatesUseCaseImpl(
  private val workloadRepository: WorkloadRepository
) : GetTaskWorkloadsBetweenDatesUseCase {

    override fun exec(task: Task, from: LocalDate, to: LocalDate): List<Workload> {
        return workloadRepository.findAllByTaskAndWorkloadCreationTimeBetween(
          task = task,
          from = from.atStartOfDay(),
          to = to.plusDays(1).atStartOfDay()
        )
    }
}