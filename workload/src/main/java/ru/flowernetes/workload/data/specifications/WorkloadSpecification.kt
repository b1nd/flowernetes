package ru.flowernetes.workload.data.specifications

import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.domain.Specification.where
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.task.TaskStatus
import ru.flowernetes.entity.team.Team
import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.workload.api.domain.dto.WorkloadFilter
import java.time.LocalDateTime

fun WorkloadFilter.toSpecification(): Specification<Workload>? {
    return where(id?.let(::workloadSpecification))
      ?.and(teamId?.let(::teamSpecification))
      ?.and(taskId?.let(::taskSpecification))
      ?.and(fromDate?.let { fromDateSpecification(it.atStartOfDay()) })
      ?.and(toDate?.let { toDateSpecification(it.plusDays(1).atStartOfDay()) })
      ?.and(taskStatus?.let(::taskStatusSpecification))
}

fun teamSpecification(teamId: Long) = Specification<Workload> { root, _, criteriaBuilder ->
    val workloadTask = root.join<Workload, Task>("task")
    val taskWorkflow = workloadTask.join<Task, Workflow>("workflow")
    val workflowTeam = taskWorkflow.join<Workflow, Team>("team")
    val teamPredicate = criteriaBuilder.equal(workflowTeam.get<Team>("id"), teamId)
    val publicWorkflow = criteriaBuilder.equal(taskWorkflow.get<Workflow>("isPublic"), true)
    criteriaBuilder.or(publicWorkflow, teamPredicate)
}

fun workloadSpecification(id: Long) = Specification<Workload> { root, _, criteriaBuilder ->
    criteriaBuilder.equal(root.get<Long>("id"), id)
}

fun taskSpecification(taskId: Long) = Specification<Workload> { root, _, criteriaBuilder ->
    val workloadTask = root.join<Workload, Task>("task")
    criteriaBuilder.equal(workloadTask.get<Long>("id"), taskId)
}

fun fromDateSpecification(fromDate: LocalDateTime) = Specification<Workload> { root, _, criteriaBuilder ->
    criteriaBuilder.greaterThanOrEqualTo(root.get("workloadCreationTime"), fromDate)
}

fun toDateSpecification(toDate: LocalDateTime) = Specification<Workload> { root, _, criteriaBuilder ->
    criteriaBuilder.lessThanOrEqualTo(root.get("workloadCreationTime"), toDate)
}

fun taskStatusSpecification(taskStatus: TaskStatus) = Specification<Workload> { root, _, criteriaBuilder ->
    criteriaBuilder.equal(root.get<TaskStatus>("taskStatus"), taskStatus)
}