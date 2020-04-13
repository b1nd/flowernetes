package ru.flowernetes.auth.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.auth.api.domain.entity.NoSystemRoleInfoException
import ru.flowernetes.auth.api.domain.usecase.GetSystemRoleByUsernameUseCase
import ru.flowernetes.auth.data.repo.SystemRoleRepo
import ru.flowernetes.entity.auth.SystemRole

@Component
open class GetSystemRoleByUsernameUseCaseImpl(
  private val systemRoleRepo: SystemRoleRepo
) : GetSystemRoleByUsernameUseCase {

    override fun execute(username: String): SystemRole {
        return systemRoleRepo.findByUserUsername(username) ?: throw NoSystemRoleInfoException(username)
    }
}