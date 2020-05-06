package ru.flowernetes.monitoring.domain.usecase

import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.monitoring.api.domain.entity.Topic
import ru.flowernetes.monitoring.api.domain.usecase.GetTaskStatusInfoFromWorkloadUseCase
import ru.flowernetes.monitoring.api.domain.usecase.OnWorkflowMonitoringWorkloadUpdateUseCase

@Component
open class OnWorkflowMonitoringWorkloadUpdateUseCaseImpl(
  private val messagingTemplate: SimpMessageSendingOperations,
  private val getTaskStatusInfoFromWorkloadUseCase: GetTaskStatusInfoFromWorkloadUseCase
) : OnWorkflowMonitoringWorkloadUpdateUseCase {

    @Async
    override fun execAsync(workload: Workload) {
        val taskStatusInfo = getTaskStatusInfoFromWorkloadUseCase.exec(workload)
        messagingTemplate.convertAndSend(Topic.WORKFLOW.key(workload.task.workflow.id), taskStatusInfo)
    }
}