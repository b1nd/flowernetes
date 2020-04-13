package ru.flowernetes.auth.domain.usecase

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import ru.flowernetes.auth.api.domain.RoleChecker
import ru.flowernetes.auth.api.domain.requireAll
import ru.flowernetes.auth.api.domain.usecase.ChangePasswordUseCase
import ru.flowernetes.auth.api.domain.usecase.GetCallingUserUseCase
import ru.flowernetes.auth.api.domain.usecase.GetUserByUsernameUseCase
import ru.flowernetes.auth.data.repo.UserRepo
import ru.flowernetes.entity.auth.SystemUserRole

@Component
class ChangePasswordUseCaseImpl(
  private val roleChecker: RoleChecker,
  private val getCallingUserUseCase: GetCallingUserUseCase,
  private val passwordEncoder: PasswordEncoder,
  private val getUserByUsernameUseCase: GetUserByUsernameUseCase,
  private val userRepo: UserRepo
) : ChangePasswordUseCase {

    override fun execute(username: String, password: String) {
        val callingUser = getCallingUserUseCase.execute()

        if (callingUser.username != username) {
            roleChecker.requireAll(SystemUserRole.ADMIN)

            val user = getUserByUsernameUseCase.execute(username)
            userRepo.save(user.copy(password = passwordEncoder.encode(password)))
        } else {
            userRepo.save(callingUser.copy(password = passwordEncoder.encode(password)))
        }
    }
}