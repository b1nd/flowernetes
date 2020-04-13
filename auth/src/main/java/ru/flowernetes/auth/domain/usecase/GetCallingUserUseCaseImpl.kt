package ru.flowernetes.auth.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.auth.api.domain.entity.NoUserException
import ru.flowernetes.auth.api.domain.usecase.GetCallingUserUseCase
import ru.flowernetes.auth.data.repo.UserRepo
import ru.flowernetes.auth.data.security.SecurityRepository
import ru.flowernetes.entity.auth.User

@Component
open class GetCallingUserUseCaseImpl(
  private val securityRepository: SecurityRepository,
  private val userRepo: UserRepo
) : GetCallingUserUseCase {

    override fun execute(): User {
        val username = securityRepository.getAuthenticatedUsername()
        return userRepo.findByUsername(username) ?: throw NoUserException(username)
    }
}