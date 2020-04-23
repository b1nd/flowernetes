package ru.flowernetes.task.data.repo

import org.springframework.data.jpa.repository.JpaRepository
import ru.flowernetes.entity.task.DependencyMarker
import ru.flowernetes.entity.task.Task

interface DependencyMarkerRepository : JpaRepository<DependencyMarker, Long> {
    fun findAllByDependencyTask(dependencyTask: Task): List<DependencyMarker>
    fun findAllByDependencyTaskAndTask_ScheduledIsTrue(dependencyTask: Task): List<DependencyMarker>
    fun findAllByTask(task: Task): List<DependencyMarker>
}