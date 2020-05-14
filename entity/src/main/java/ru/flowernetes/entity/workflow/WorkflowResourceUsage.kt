package ru.flowernetes.entity.workflow

import ru.flowernetes.entity.task.Task

data class WorkflowResourceUsage<T>(
  val workflow: Workflow,
  val tasks: List<Task>,
  val request: T,
  val limit: T
)