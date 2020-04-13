package ru.flowernetes.auth.data

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ru.flowernetes.entity.auth.SystemUserRole
import ru.flowernetes.entity.auth.User

class UserPrincipal(
  private val user: User,
  private val roles: Set<SystemUserRole>
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return roles
          .map { SimpleGrantedAuthority("ROLE_$it") }
          .ifEmpty { listOf(SimpleGrantedAuthority("ROLE_UNKNOWN")) }
          .toMutableList()
    }

    override fun isEnabled() = true

    override fun getUsername() = user.username

    override fun isCredentialsNonExpired() = true

    override fun getPassword() = user.password

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true
}