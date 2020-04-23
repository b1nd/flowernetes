package ru.flowernetes.workload.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.workload.api.domain.usecase.DeleteWorkloadsByTaskUseCase
import ru.flowernetes.workload.data.repo.WorkloadRepository

@Component
class DeleteWorkloadsByTaskUseCaseImpl(
  private val workloadRepository: WorkloadRepository
) : DeleteWorkloadsByTaskUseCase {

    override fun exec(task: Task) {
        workloadRepository.deleteAll(workloadRepository.findAllByTask(task))
    }
}