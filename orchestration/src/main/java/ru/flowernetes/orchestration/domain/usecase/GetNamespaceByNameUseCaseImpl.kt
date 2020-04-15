package ru.flowernetes.orchestration.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.orchestration.Namespace
import ru.flowernetes.orchestration.api.domain.usecase.CheckNamespaceExistsUseCase
import ru.flowernetes.orchestration.api.domain.usecase.GetNamespaceByNameUseCase
import ru.flowernetes.orchestration.api.domain.usecase.GetNamespaceResourceQuotaUseCase

@Component
class GetNamespaceByNameUseCaseImpl(
  private val checkNamespaceExistsUseCase: CheckNamespaceExistsUseCase,
  private val getNamespaceResourceQuotaUseCase: GetNamespaceResourceQuotaUseCase
) : GetNamespaceByNameUseCase {

    override fun exec(namespaceName: String): Namespace {
        checkNamespaceExistsUseCase.exec(namespaceName)
        val resourceQuota = getNamespaceResourceQuotaUseCase.exec(namespaceName)

        return Namespace(namespaceName, resourceQuota)
    }
}