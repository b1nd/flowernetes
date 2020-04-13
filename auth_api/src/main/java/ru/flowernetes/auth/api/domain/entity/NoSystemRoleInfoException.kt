package ru.flowernetes.auth.api.domain.entity

class NoSystemRoleInfoException(username: String) : Throwable("No role info for user: $username")