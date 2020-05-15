package ru.flowernetes.task.data.dto

import ru.flowernetes.entity.task.Task

data class TasksDto(
  val items: List<Task>
)