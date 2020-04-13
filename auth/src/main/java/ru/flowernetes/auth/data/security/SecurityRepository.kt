package ru.flowernetes.auth.data.security

interface SecurityRepository {
    fun getAuthenticatedUsername(): String
}