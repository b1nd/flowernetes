package ru.flowernetes.auth.data.repo

import org.springframework.data.jpa.repository.JpaRepository
import ru.flowernetes.entity.auth.SystemRole

interface SystemRoleRepo : JpaRepository<SystemRole, Long> {
    fun findByUserUsername(username: String): SystemRole?
}