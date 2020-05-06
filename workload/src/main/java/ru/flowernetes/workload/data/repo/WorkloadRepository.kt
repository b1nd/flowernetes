package ru.flowernetes.workload.data.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workload.Workload

interface WorkloadRepository : JpaRepository<Workload, Long>, JpaSpecificationExecutor<Workload> {
    fun findAllByTask(task: Task): List<Workload>
    fun findTopByTaskOrderByWorkloadCreationTimeDesc(task: Task): Workload?
}