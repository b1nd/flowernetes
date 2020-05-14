package ru.flowernetes.orchestration.api.domain.usecase

import ru.flowernetes.entity.orchestration.Namespace

interface GetNamespaceByNameOrEmptyUseCase {
    fun exec(namespaceName: String): Namespace
}