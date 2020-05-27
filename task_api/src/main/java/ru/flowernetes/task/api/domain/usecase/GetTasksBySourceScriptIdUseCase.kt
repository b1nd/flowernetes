package ru.flowernetes.task.api.domain.usecase

import ru.flowernetes.entity.task.Task

interface GetTasksBySourceScriptIdUseCase {
    fun exec(sourceScriptId: String): List<Task>
}