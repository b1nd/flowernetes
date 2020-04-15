package ru.flowernetes.orchestration.api.domain.usecase

import ru.flowernetes.entity.orchestration.ResourceQuota

interface GetNamespaceResourceQuotaUseCase {
    fun exec(namespaceName: String): ResourceQuota
}