package ru.flowernetes.task.api.domain.usecase

import ru.flowernetes.entity.task.Task

interface CheckThereAreNoDependenciesOnTaskUseCase {
    fun exec(task: Task)
}