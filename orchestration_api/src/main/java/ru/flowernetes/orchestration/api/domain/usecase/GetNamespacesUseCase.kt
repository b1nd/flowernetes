package ru.flowernetes.orchestration.api.domain.usecase

import ru.flowernetes.entity.orchestration.Namespace

interface GetNamespacesUseCase {
    fun exec(): List<Namespace>
}