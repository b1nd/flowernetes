package ru.flowernetes.task.api.domain.usecase

import ru.flowernetes.entity.task.Task

interface MarkAllDependentTasksAndGetThemUseCase {
    fun exec(task: Task): List<Task>
}