package ru.flowernetes.workload.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.workload.api.domain.model.WorkloadModel
import ru.flowernetes.workload.api.domain.observable.WorkloadObservable
import ru.flowernetes.workload.api.domain.usecase.AddWorkloadUseCase
import ru.flowernetes.workload.data.mapper.WorkloadModelMapper
import ru.flowernetes.workload.data.repo.WorkloadRepository

@Component
class AddWorkloadUseCaseImpl(
  private val workloadRepository: WorkloadRepository,
  private val workloadObservable: WorkloadObservable,
  private val workloadModelMapper: WorkloadModelMapper
) : AddWorkloadUseCase {

    override fun exec(workloadModel: WorkloadModel): Workload {
        return workloadRepository
          .save(workloadModelMapper.map(workloadModel))
          .also(workloadObservable::notify)
    }
}