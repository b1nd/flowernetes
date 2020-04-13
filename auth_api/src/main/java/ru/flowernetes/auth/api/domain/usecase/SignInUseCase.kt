package ru.flowernetes.auth.api.domain.usecase

import ru.flowernetes.auth.api.domain.dto.Credentials
import ru.flowernetes.auth.api.domain.dto.TokenDto

interface SignInUseCase {
    fun execute(credentials: Credentials): TokenDto
}