package ru.flowernetes.task.data.repo

import org.springframework.data.jpa.repository.JpaRepository
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.task.TaskDependencies

interface TaskDependenciesRepository : JpaRepository<TaskDependencies, Long> {
    fun findByTask(task: Task): TaskDependencies
}