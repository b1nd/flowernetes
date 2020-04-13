package ru.flowernetes.auth.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.auth.api.domain.usecase.GetCallingUserSystemRoleUseCase
import ru.flowernetes.auth.api.domain.usecase.GetCallingUserUseCase
import ru.flowernetes.auth.api.domain.usecase.GetSystemRoleByUsernameUseCase
import ru.flowernetes.entity.auth.SystemRole

@Component
class GetCallingUserSystemRoleUseCaseImpl(
  private val getCallingUserUseCase: GetCallingUserUseCase,
  private val getSystemRoleByUsername: GetSystemRoleByUsernameUseCase
) : GetCallingUserSystemRoleUseCase {

    override fun execute(): SystemRole {
        val callingUser = getCallingUserUseCase.execute()
        return getSystemRoleByUsername.execute(callingUser.username)
    }
}