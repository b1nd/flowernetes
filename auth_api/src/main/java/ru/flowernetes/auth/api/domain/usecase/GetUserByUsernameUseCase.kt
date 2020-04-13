package ru.flowernetes.auth.api.domain.usecase

import ru.flowernetes.entity.auth.User

interface GetUserByUsernameUseCase {
    fun execute(username: String): User
}