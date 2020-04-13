package ru.flowernetes.auth.api.domain.entity

class UserExistsException(username: String) : Throwable("User $username already exists")