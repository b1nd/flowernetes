package ru.flowernetes.auth.api.domain.usecase

import ru.flowernetes.entity.auth.SystemUserRole

interface CheckCallerHasSystemRoleUseCase {
    fun execute(role: SystemUserRole): Boolean
}