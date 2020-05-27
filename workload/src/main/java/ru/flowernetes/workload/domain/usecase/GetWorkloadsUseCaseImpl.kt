package ru.flowernetes.workload.domain.usecase

import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import ru.flowernetes.auth.api.domain.usecase.GetCallingUserSystemRoleUseCase
import ru.flowernetes.entity.auth.SystemUserRole
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.pagination.api.domain.entity.Page
import ru.flowernetes.pagination.api.domain.entity.PageRequest
import ru.flowernetes.team.api.domain.usecase.GetCallingUserTeamUseCase
import ru.flowernetes.util.extensions.toPage
import ru.flowernetes.util.extensions.toSpringPageRequest
import ru.flowernetes.workload.api.domain.dto.WorkloadFilter
import ru.flowernetes.workload.api.domain.usecase.GetWorkloadsUseCase
import ru.flowernetes.workload.data.repo.WorkloadRepository
import ru.flowernetes.workload.data.specifications.toSpecification

@Component
class GetWorkloadsUseCaseImpl(
  private val workloadRepository: WorkloadRepository,
  private val getCallingUserSystemRoleUseCase: GetCallingUserSystemRoleUseCase,
  private val getCallingUserTeamUseCase: GetCallingUserTeamUseCase
) : GetWorkloadsUseCase {

    override fun exec(pageRequest: PageRequest, workloadFilter: WorkloadFilter?): Page<Workload> {
        val specification = addRoleRestrictionsToFilter(workloadFilter ?: WorkloadFilter())
        return workloadRepository.findAll(specification, pageRequest.toSpringPageRequest()).toPage()
    }

    private fun addRoleRestrictionsToFilter(workloadFilter: WorkloadFilter): Specification<Workload>? {
        return when (getCallingUserSystemRoleUseCase.execute().role) {
            SystemUserRole.ADMIN -> workloadFilter.copy(teamId = null).toSpecification()
            SystemUserRole.TEAM -> workloadFilter.copy(teamId = getCallingUserTeamUseCase.exec().id).toSpecification()
        }
    }
}