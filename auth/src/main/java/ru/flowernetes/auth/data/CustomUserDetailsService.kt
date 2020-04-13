package ru.flowernetes.auth.data

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.flowernetes.auth.api.domain.usecase.GetSystemRoleByUsernameUseCase
import ru.flowernetes.auth.data.repo.UserRepo

@Service
open class CustomUserDetailsService(
  private val userRepo: UserRepo,
  private val getSystemRoleByUsernameUseCase: GetSystemRoleByUsernameUseCase
) : UserDetailsService {

    @Transactional
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepo.findByUsername(username) ?: throw UsernameNotFoundException("Not found $username")
        val roles = getSystemRoleByUsernameUseCase.runCatching { setOf(execute(username).role) }
          .getOrElse { setOf() }

        return UserPrincipal(user, roles)
    }
}