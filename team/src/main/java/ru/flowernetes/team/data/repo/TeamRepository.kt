package ru.flowernetes.team.data.repo

import org.springframework.data.jpa.repository.JpaRepository
import ru.flowernetes.entity.team.Team

interface TeamRepository : JpaRepository<Team, Long> {
    fun findByName(name: String): Team?
}