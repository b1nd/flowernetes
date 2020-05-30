package ru.flowernetes.task.api.domain.entity

import ru.flowernetes.entity.task.Task

class CannotDeleteDependentTaskException(task: Task, dependentTasks: List<Task>) : IllegalArgumentException(
  "Cannot delete task ${task.workflowWithName()}, " +
  "there are dependencies on it: ${dependentTasks.joinToString { it.workflowWithName() }}"
)