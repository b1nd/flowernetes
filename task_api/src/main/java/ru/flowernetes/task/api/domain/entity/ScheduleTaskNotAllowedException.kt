package ru.flowernetes.task.api.domain.entity

import ru.flowernetes.entity.task.Task

class ScheduleTaskNotAllowedException(task: Task) :
  IllegalArgumentException("Task schedule can be changed only by admin or workflow owner: $task")