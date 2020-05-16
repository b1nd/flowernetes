package ru.flowernetes.monitoring.domain.usecase

import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Component
import ru.flowernetes.entity.monitoring.TaskStatusInfo
import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.monitoring.api.domain.entity.Topic
import ru.flowernetes.monitoring.api.domain.usecase.SendWorkflowTaskStatusMessageUseCase

@Component
class SendWorkflowTaskStatusMessageUseCaseImpl(
  private val messagingTemplate: SimpMessageSendingOperations
) : SendWorkflowTaskStatusMessageUseCase {

    override fun exec(workflow: Workflow, taskStatusInfo: TaskStatusInfo) {
        messagingTemplate.convertAndSend(Topic.WORKFLOW.key(workflow.id), taskStatusInfo)
    }
}