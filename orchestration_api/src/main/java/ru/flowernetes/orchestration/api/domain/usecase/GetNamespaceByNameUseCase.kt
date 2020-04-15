package ru.flowernetes.orchestration.api.domain.usecase

import ru.flowernetes.entity.orchestration.Namespace

interface GetNamespaceByNameUseCase {
    fun exec(namespaceName: String): Namespace
}