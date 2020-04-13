package ru.flowernetes.auth.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import ru.flowernetes.auth.api.domain.dto.Credentials
import ru.flowernetes.auth.api.domain.usecase.SignInUseCase
import ru.flowernetes.auth.data.AuthenticatorAdapter
import ru.flowernetes.auth.data.jwt.JwtTokenProvider
import kotlin.test.assertFails

class SignInUseCaseImplTest {

    private lateinit var useCase: SignInUseCase
    private lateinit var authenticator: AuthenticatorAdapter
    private lateinit var tokenProvider: JwtTokenProvider

    @Before
    fun setUp() {
        authenticator = mock {}
        tokenProvider = mock {}
        useCase = SignInUseCaseImpl(authenticator, tokenProvider)
    }

    @Test
    fun `when authenticator fails should rethrow`() {
        val exception = RuntimeException("exception")
        val credentials = Credentials("login", "pass")
        whenever(authenticator.authenticate(credentials)).thenThrow(exception)

        assertFails { useCase.execute(credentials) }
    }

    @Test
    fun `when authenticator completes successfully should generate token and return it`() {
        val credentials = Credentials("login", "pass")

        val token = "token"
        whenever(tokenProvider.generateToken(credentials.login)).then { token }

        assertThat(useCase.execute(credentials).token).isEqualTo(token)
    }
}