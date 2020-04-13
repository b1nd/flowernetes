package ru.flowernetes.auth.api.domain.usecase

import ru.flowernetes.entity.auth.SystemRole

interface GetCallingUserSystemRoleUseCase {
    fun execute(): SystemRole
}