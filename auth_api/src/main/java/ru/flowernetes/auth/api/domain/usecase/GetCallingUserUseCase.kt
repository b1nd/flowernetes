package ru.flowernetes.auth.api.domain.usecase

import ru.flowernetes.entity.auth.User

interface GetCallingUserUseCase {
    fun execute(): User
}