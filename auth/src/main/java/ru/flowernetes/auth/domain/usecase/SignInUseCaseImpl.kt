package ru.flowernetes.auth.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.auth.api.domain.dto.Credentials
import ru.flowernetes.auth.api.domain.dto.TokenDto
import ru.flowernetes.auth.api.domain.usecase.SignInUseCase
import ru.flowernetes.auth.data.AuthenticatorAdapter
import ru.flowernetes.auth.data.jwt.JwtTokenProvider

@Component
open class SignInUseCaseImpl(
  private val authenticator: AuthenticatorAdapter,
  private val tokenProvider: JwtTokenProvider
) : SignInUseCase {

    override fun execute(credentials: Credentials): TokenDto {
        authenticator.authenticate(credentials)

        val token = tokenProvider.generateToken(credentials.login)

        return TokenDto(token)
    }
}