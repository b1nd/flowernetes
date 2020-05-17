package ru.flowernetes.monitoring.domain.usecase

import org.slf4j.Logger
import org.slf4j.LoggerFactory
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

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun exec(workflow: Workflow, taskStatusInfo: TaskStatusInfo) {
        kotlin.runCatching {
            messagingTemplate.convertAndSend(Topic.WORKFLOW.key(workflow.id), taskStatusInfo)
        }.onFailure {
            log.error(it.message, it)
        }
    }
}