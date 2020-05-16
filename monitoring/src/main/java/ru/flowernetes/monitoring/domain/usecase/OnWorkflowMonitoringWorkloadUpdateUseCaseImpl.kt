package ru.flowernetes.monitoring.domain.usecase

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.monitoring.api.domain.usecase.GetTaskStatusInfoFromWorkloadUseCase
import ru.flowernetes.monitoring.api.domain.usecase.OnWorkflowMonitoringWorkloadUpdateUseCase
import ru.flowernetes.monitoring.api.domain.usecase.SendWorkflowTaskStatusMessageUseCase

@Component
open class OnWorkflowMonitoringWorkloadUpdateUseCaseImpl(
  private val sendWorkflowTaskStatusMessageUseCase: SendWorkflowTaskStatusMessageUseCase,
  private val getTaskStatusInfoFromWorkloadUseCase: GetTaskStatusInfoFromWorkloadUseCase
) : OnWorkflowMonitoringWorkloadUpdateUseCase {

    @Async
    override fun execAsync(workload: Workload) {
        val taskStatusInfo = getTaskStatusInfoFromWorkloadUseCase.exec(workload)
        sendWorkflowTaskStatusMessageUseCase.exec(workload.task.workflow, taskStatusInfo)
    }
}