package ru.flowernetes.task.data.repo

import org.springframework.data.jpa.repository.JpaRepository
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workflow.Workflow

interface TaskRepository : JpaRepository<Task, Long> {
    fun findAllByWorkflow(workflow: Workflow): List<Task>
    fun findAllByScheduledIsTrue(): List<Task>
}