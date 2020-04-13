package ru.flowernetes.auth.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.auth.api.domain.entity.NoUserException
import ru.flowernetes.auth.api.domain.usecase.GetUserByUsernameUseCase
import ru.flowernetes.auth.data.repo.UserRepo
import ru.flowernetes.entity.auth.User

@Component
class GetUserByUsernameUseCaseImpl(
  private val userRepo: UserRepo
) : GetUserByUsernameUseCase {

    override fun execute(username: String): User {
        return userRepo.findByUsername(username) ?: throw NoUserException(username)
    }
}