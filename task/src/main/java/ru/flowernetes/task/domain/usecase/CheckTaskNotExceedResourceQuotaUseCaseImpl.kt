package ru.flowernetes.task.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.orchestration.api.domain.usecase.GetNamespaceByNameOrEmptyUseCase
import ru.flowernetes.task.api.domain.entity.TaskExceedsResourceQuotaException
import ru.flowernetes.task.api.domain.usecase.CheckTaskNotExceedResourceQuotaUseCase

@Component
class CheckTaskNotExceedResourceQuotaUseCaseImpl(
  private val getNamespaceByNameOrEmptyUseCase: GetNamespaceByNameOrEmptyUseCase
) : CheckTaskNotExceedResourceQuotaUseCase {

    override fun exec(task: Task) {
        val namespace = getNamespaceByNameOrEmptyUseCase.exec(task.workflow.team.namespace)
        val quota = namespace.resourceQuota

        if (
          task.cpuRequest > quota.cpuRequest ||
          task.cpuLimit > quota.cpuLimit ||
          task.memoryRequest > quota.memoryRequest ||
          task.memoryLimit > quota.memoryLimit
        ) {
            throw TaskExceedsResourceQuotaException(task, quota)
        }
    }
}