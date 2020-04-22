package ru.flowernetes.task.api.domain.entity

import ru.flowernetes.entity.task.Task

class RunTaskNotAllowedException(task: Task) :
  IllegalArgumentException("Task can be run only by admin or workflow owner: $task")