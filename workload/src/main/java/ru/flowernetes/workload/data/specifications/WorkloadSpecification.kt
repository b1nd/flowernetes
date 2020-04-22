package ru.flowernetes.workload.data.specifications

import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.domain.Specification.where
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.team.Team
import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.workload.api.domain.dto.WorkloadFilter

fun WorkloadFilter.toSpecification(): Specification<Workload> {
    return where(team?.let(::teamSpecification))
}

fun teamSpecification(team: Team) = Specification<Workload> { root, _, criteriaBuilder ->
    val workloadTask = root.join<Workload, Task>("task")
    val taskWorkflow = workloadTask.join<Task, Workflow>("workflow")
    val workflowTeam = taskWorkflow.join<Workflow, Team>("team")
    val teamPredicate = criteriaBuilder.equal(workflowTeam.get<Team>("id"), team.id)
    val publicWorkflow = criteriaBuilder.equal(taskWorkflow.get<Workflow>("isPublic"), true)
    criteriaBuilder.or(publicWorkflow, teamPredicate)
}