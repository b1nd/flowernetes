package ru.flowernetes.auth.api.domain.dto

import ru.flowernetes.entity.auth.SystemUserRole

data class UserInfoDto(
  val username: String,
  val systemUserRole: SystemUserRole
)