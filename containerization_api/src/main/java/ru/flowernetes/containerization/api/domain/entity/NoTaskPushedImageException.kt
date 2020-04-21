package ru.flowernetes.containerization.api.domain.entity

import ru.flowernetes.entity.task.Task

class NoTaskPushedImageException(task: Task) : NoSuchElementException("Task $task has no pushed image")