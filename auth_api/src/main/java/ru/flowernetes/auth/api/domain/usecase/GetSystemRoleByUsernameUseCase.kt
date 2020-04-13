package ru.flowernetes.auth.api.domain.usecase

import ru.flowernetes.entity.auth.SystemRole

interface GetSystemRoleByUsernameUseCase {
    fun execute(username: String): SystemRole
}