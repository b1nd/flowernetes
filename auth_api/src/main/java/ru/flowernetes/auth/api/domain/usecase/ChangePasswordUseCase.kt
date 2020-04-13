package ru.flowernetes.auth.api.domain.usecase

interface ChangePasswordUseCase {
    fun execute(username: String, password: String)
}