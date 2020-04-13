package ru.flowernetes.auth.data.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.flowernetes.auth.api.domain.dto.Credentials
import ru.flowernetes.auth.api.domain.dto.TokenDto
import ru.flowernetes.auth.api.domain.dto.UserInfoDto
import ru.flowernetes.auth.api.domain.usecase.GetCallingUserSystemRoleUseCase
import ru.flowernetes.auth.api.domain.usecase.SignInUseCase
import ru.flowernetes.auth.api.domain.usecase.SignUpUseCase
import ru.flowernetes.entity.auth.SystemUserRole

@RestController
open class AuthController(
  private val signInUseCase: SignInUseCase,
  private val signUpUseCase: SignUpUseCase,
  private val getCallingUserSystemRoleUseCase: GetCallingUserSystemRoleUseCase
) {

    @PostMapping("/login")
    fun login(@RequestBody credentials: Credentials): TokenDto {
        return signInUseCase.execute(credentials)
    }

    @PostMapping("/signup")
    fun registerUser(@RequestBody credentials: Credentials) {
        signUpUseCase.execute(credentials, SystemUserRole.TEAM)
    }

    @GetMapping("/auth/info")
    fun getSystemUserRole(): UserInfoDto {
        return getCallingUserSystemRoleUseCase.execute()
          .let { UserInfoDto(it.user.username, it.role) }
    }
}