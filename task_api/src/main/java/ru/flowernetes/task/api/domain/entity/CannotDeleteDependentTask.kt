package ru.flowernetes.task.api.domain.entity

import ru.flowernetes.entity.task.Task

class CannotDeleteDependentTask(task: Task, dependentTasks: List<Task>)
    : IllegalArgumentException("Cannot delete task $task, there are dependencies on it: $dependentTasks")