package ru.flowernetes.auth.domain

import org.springframework.stereotype.Component
import ru.flowernetes.auth.api.domain.RoleChecker
import ru.flowernetes.auth.api.domain.entity.NotAllowedException
import ru.flowernetes.auth.api.domain.usecase.CheckCallerHasSystemRoleUseCase
import ru.flowernetes.entity.auth.SystemUserRole

@Component
class RoleCheckerImpl(
  private val checkCallerHasSystemRoleUseCase: CheckCallerHasSystemRoleUseCase
) : RoleChecker {

    override fun startCheck(): RoleChecker.Setup {
        return SetupImpl()
    }

    inner class SetupImpl : RoleChecker.Setup {
        private val systemRoles = mutableListOf<SystemUserRole>()

        override fun require(vararg roles: SystemUserRole) = apply {
            systemRoles.addAll(roles)
        }

        override fun requireAnySpecified() {
            if (gatherRoleChecks().any { it.value }.not()) throwNotAllowed("any")
        }

        override fun requireAllSpecified() {
            if (gatherRoleChecks().all { it.value }.not()) throwNotAllowed("all")
        }

        private fun gatherRoleChecks(): List<Lazy<Boolean>> {
            return systemRoles.map { lazy { checkCallerHasSystemRoleUseCase.execute(it) } }
        }

        private fun throwNotAllowed(infix: String) {
            throw NotAllowedException("required $infix roles: $systemRoles")
        }
    }
}