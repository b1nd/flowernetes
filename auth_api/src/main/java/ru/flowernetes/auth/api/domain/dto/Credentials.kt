package ru.flowernetes.auth.api.domain.dto

data class Credentials(
  val login: String,
  val password: String
)