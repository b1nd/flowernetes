package ru.flowernetes.workload.domain.usecase

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.workload.api.domain.entity.NoSuchWorkloadException
import ru.flowernetes.workload.api.domain.usecase.GetWorkloadByIdUseCase
import ru.flowernetes.workload.data.repo.WorkloadRepository

@Component
class GetWorkloadByIdUseCaseImpl(
  private val workloadRepository: WorkloadRepository
) : GetWorkloadByIdUseCase {

    override fun exec(id: Long): Workload {
        return workloadRepository.findByIdOrNull(id) ?: throw NoSuchWorkloadException(id)
    }
}