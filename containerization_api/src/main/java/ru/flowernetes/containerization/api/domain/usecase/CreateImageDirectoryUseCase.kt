package ru.flowernetes.containerization.api.domain.usecase

import ru.flowernetes.entity.task.Task
import java.nio.file.Path

interface CreateImageDirectoryUseCase {
    fun exec(task: Task): Path
}