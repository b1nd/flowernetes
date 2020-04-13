package ru.flowernetes.auth.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.auth.api.domain.usecase.DeleteUserUseCase
import ru.flowernetes.auth.data.repo.UserRepo
import ru.flowernetes.entity.auth.User

@Component
class DeleteUserUseCaseImpl(
  private val userRepo: UserRepo
) : DeleteUserUseCase {

    override fun execute(user: User) {
        userRepo.delete(user)
    }
}