package ru.flowernetes.workload.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.workload.api.domain.usecase.GetTaskLastWorkloadUseCase
import ru.flowernetes.workload.data.repo.WorkloadRepository

@Component
class GetTaskLastWorkloadUseCaseImpl(
  private val workloadRepository: WorkloadRepository
) : GetTaskLastWorkloadUseCase {

    override fun exec(task: Task): Workload? {
        return workloadRepository.findTopByTaskOrderByWorkloadCreationTimeDesc(task)
    }
}