package ru.flowernetes.team.data.repo

import org.springframework.data.jpa.repository.JpaRepository
import ru.flowernetes.entity.team.TeamUser

interface TeamUserRepository : JpaRepository<TeamUser, Long> {
    fun findByUserUsername(username: String): TeamUser
}