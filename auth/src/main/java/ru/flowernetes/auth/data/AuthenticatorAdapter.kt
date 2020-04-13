package ru.flowernetes.auth.data

import ru.flowernetes.auth.api.domain.dto.Credentials

interface AuthenticatorAdapter {
    fun authenticate(credentials: Credentials)
}