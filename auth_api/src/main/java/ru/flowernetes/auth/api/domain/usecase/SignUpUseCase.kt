package ru.flowernetes.auth.api.domain.usecase

import ru.flowernetes.auth.api.domain.dto.Credentials
import ru.flowernetes.entity.auth.SystemUserRole
import ru.flowernetes.entity.auth.User

interface SignUpUseCase {
    fun execute(credentials: Credentials, role: SystemUserRole): User
}