package ru.flowernetes.containerization.data.generator

import ru.flowernetes.entity.task.TaskInfo
import java.nio.file.Path

interface TaskInfoDockerfileGenerator {
    fun gen(taskInfo: TaskInfo, destDir: Path)
}