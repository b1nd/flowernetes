package ru.flowernetes.auth.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.auth.api.domain.usecase.CheckCallerHasSystemRoleUseCase
import ru.flowernetes.auth.api.domain.usecase.GetCallingUserSystemRoleUseCase
import ru.flowernetes.entity.auth.SystemUserRole

@Component
class CheckCallerHasSystemRoleUseCaseImpl(
  private val getCallingUserSystemRoleUseCase: GetCallingUserSystemRoleUseCase
) : CheckCallerHasSystemRoleUseCase {

    override fun execute(role: SystemUserRole): Boolean {
        val callingRole = getCallingUserSystemRoleUseCase.execute()
        return callingRole.role == role
    }
}