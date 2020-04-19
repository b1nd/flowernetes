package ru.flowernetes.task.api.domain.entity

class NoSuchTaskException(taskId: Long) : NoSuchElementException("There is no task with id: $taskId")