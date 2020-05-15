package ru.flowernetes.task.api.domain.entity

import ru.flowernetes.entity.orchestration.ResourceQuota
import ru.flowernetes.entity.task.Task

class TaskExceedsResourceQuotaException(task: Task, resourceQuota: ResourceQuota)
    : IllegalArgumentException("Task ${task.name} exceeds resource quota: $resourceQuota")