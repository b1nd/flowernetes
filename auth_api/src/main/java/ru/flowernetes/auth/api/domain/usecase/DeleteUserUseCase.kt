package ru.flowernetes.auth.api.domain.usecase

import ru.flowernetes.entity.auth.User

interface DeleteUserUseCase {
    fun execute(user: User)
}