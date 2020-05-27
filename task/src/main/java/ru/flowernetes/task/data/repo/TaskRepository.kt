package ru.flowernetes.task.data.repo

import org.springframework.data.jpa.repository.JpaRepository
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.team.Team
import ru.flowernetes.entity.workflow.Workflow

interface TaskRepository : JpaRepository<Task, Long> {
    fun findAllByWorkflow(workflow: Workflow): List<Task>
    fun findAllByScheduledIsTrue(): List<Task>
    fun findAllByWorkflow_TeamOrWorkflow_IsPublicIsTrue(team: Team): List<Task>
    fun findByWorkflowAndName(workflow: Workflow, taskName: String): Task?
    fun findAllBySourceScriptId(sourceScriptId: String): List<Task>
}