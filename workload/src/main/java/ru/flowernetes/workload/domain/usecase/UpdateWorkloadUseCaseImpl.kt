package ru.flowernetes.workload.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.workload.api.domain.observable.WorkloadObservable
import ru.flowernetes.workload.api.domain.usecase.UpdateWorkloadUseCase
import ru.flowernetes.workload.data.repo.WorkloadRepository

@Component
class UpdateWorkloadUseCaseImpl(
  private val workloadRepository: WorkloadRepository,
  private val workloadObservable: WorkloadObservable
) : UpdateWorkloadUseCase {

    override fun exec(workload: Workload): Workload {
        return workloadRepository
          .save(workload)
          .also(workloadObservable::notify)
    }
}