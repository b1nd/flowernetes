package ru.flowernetes.task.api.domain.entity

import ru.flowernetes.entity.task.Task

class TaskControlNotAllowedException(task: Task) :
  IllegalArgumentException("Task can be controlled only by admin or workflow owner: ${task.name}")