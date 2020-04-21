package ru.flowernetes.entity.task

import ru.flowernetes.entity.script.SourceScript

data class TaskInfo(
  val task: Task,
  val sourceScript: SourceScript
)