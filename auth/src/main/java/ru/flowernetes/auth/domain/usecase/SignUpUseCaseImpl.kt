package ru.flowernetes.auth.domain.usecase

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import ru.flowernetes.auth.api.domain.RoleChecker
import ru.flowernetes.auth.api.domain.dto.Credentials
import ru.flowernetes.auth.api.domain.entity.UserExistsException
import ru.flowernetes.auth.api.domain.requireAll
import ru.flowernetes.auth.api.domain.usecase.SignUpUseCase
import ru.flowernetes.auth.data.repo.SystemRoleRepo
import ru.flowernetes.auth.data.repo.UserRepo
import ru.flowernetes.entity.auth.SystemRole
import ru.flowernetes.entity.auth.SystemUserRole
import ru.flowernetes.entity.auth.User

@Component
open class SignUpUseCaseImpl(
  private val userRepo: UserRepo,
  private val systemRoleRepo: SystemRoleRepo,
  private val passwordEncoder: PasswordEncoder,
  private val roleChecker: RoleChecker
) : SignUpUseCase {

    override fun execute(credentials: Credentials, role: SystemUserRole): User {
        roleChecker.requireAll(SystemUserRole.ADMIN)

        checkThatUsernameIsNotTaken(credentials.login)

        return userRepo.save(User(credentials.login, passwordEncoder.encode(credentials.password))).also {
            systemRoleRepo.save(SystemRole(user = it, role = role))
        }
    }

    private fun checkThatUsernameIsNotTaken(login: String) {
        if (userRepo.findByUsername(login) != null) throw UserExistsException(login)
    }
}