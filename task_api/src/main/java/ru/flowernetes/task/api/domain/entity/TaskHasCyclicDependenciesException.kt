package ru.flowernetes.task.api.domain.entity

import ru.flowernetes.entity.task.Task

class TaskHasCyclicDependenciesException(task: Task)
    : IllegalArgumentException("Task ${task.name} has cyclic dependencies")