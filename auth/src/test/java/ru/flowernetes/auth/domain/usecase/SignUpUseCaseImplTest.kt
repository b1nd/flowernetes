package ru.flowernetes.auth.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.springframework.security.crypto.password.PasswordEncoder
import ru.flowernetes.auth.api.domain.RoleChecker
import ru.flowernetes.auth.api.domain.dto.Credentials
import ru.flowernetes.auth.api.domain.entity.UserExistsException
import ru.flowernetes.auth.api.domain.usecase.SignUpUseCase
import ru.flowernetes.auth.data.repo.SystemRoleRepo
import ru.flowernetes.auth.data.repo.UserRepo
import ru.flowernetes.entity.auth.SystemUserRole
import ru.flowernetes.entity.auth.User
import kotlin.test.assertFailsWith

class SignUpUseCaseImplTest {

    private lateinit var useCase: SignUpUseCase
    private lateinit var userRepo: UserRepo
    private lateinit var systemRoleRepo: SystemRoleRepo
    private lateinit var passwordEncoder: PasswordEncoder
    private lateinit var roleChecker: RoleChecker

    @Before
    fun setUp() {
        userRepo = mock {}
        systemRoleRepo = mock {}
        passwordEncoder = mock {}
        roleChecker = mock {}

        useCase = SignUpUseCaseImpl(
          userRepo,
          systemRoleRepo,
          passwordEncoder,
          roleChecker
        )
    }

    @Test
    fun `when sign up user with login that already exists should throw`() {
        val takenUsername = "a"
        val credentials = Credentials(takenUsername, "p")
        whenever(userRepo.findByUsername(takenUsername)).then { mock<User>() }

        assertFailsWith<UserExistsException> { useCase.execute(credentials, SystemUserRole.ADMIN) }
    }
}