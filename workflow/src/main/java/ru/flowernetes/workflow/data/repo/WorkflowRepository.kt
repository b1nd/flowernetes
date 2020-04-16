package ru.flowernetes.workflow.data.repo

import org.springframework.data.jpa.repository.JpaRepository
import ru.flowernetes.entity.team.Team
import ru.flowernetes.entity.workflow.Workflow

interface WorkflowRepository : JpaRepository<Workflow, Long> {
    fun findAllByTeam(team: Team): List<Workflow>
    fun findAllByIsPublicIsTrueOrTeam(team: Team): List<Workflow>
}