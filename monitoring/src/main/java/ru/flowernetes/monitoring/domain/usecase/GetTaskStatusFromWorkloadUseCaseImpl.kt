package ru.flowernetes.monitoring.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.TaskStatus
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.monitoring.api.domain.usecase.GetTaskStatusFromWorkloadUseCase

@Component
class GetTaskStatusFromWorkloadUseCaseImpl : GetTaskStatusFromWorkloadUseCase {
    override fun exec(workload: Workload): TaskStatus {
        if (!workload.task.scheduled) return workload.taskStatus

        return when (workload.taskStatus) {
            TaskStatus.SUCCESS -> TaskStatus.WAITING
            else -> workload.taskStatus
        }
    }
}