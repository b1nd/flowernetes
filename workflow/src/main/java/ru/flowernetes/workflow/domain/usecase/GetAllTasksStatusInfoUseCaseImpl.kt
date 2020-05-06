package ru.flowernetes.workflow.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.monitoring.TaskStatusInfo
import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.monitoring.api.domain.usecase.GetTaskStatusInfoUseCase
import ru.flowernetes.task.api.domain.usecase.GetAllTasksByWorkflowUseCase
import ru.flowernetes.workflow.api.domain.usecase.GetAllTasksStatusInfoUseCase

@Component
class GetAllTasksStatusInfoUseCaseImpl(
  private val getAllTasksByWorkflowUseCase: GetAllTasksByWorkflowUseCase,
  private val getTaskStatusInfoUseCase: GetTaskStatusInfoUseCase
) : GetAllTasksStatusInfoUseCase {

    override fun exec(workflow: Workflow): List<TaskStatusInfo> {
        return getAllTasksByWorkflowUseCase.exec(workflow).map(getTaskStatusInfoUseCase::exec)
    }
}