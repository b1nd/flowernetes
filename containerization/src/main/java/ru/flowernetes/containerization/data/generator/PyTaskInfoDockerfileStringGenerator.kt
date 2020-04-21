package ru.flowernetes.containerization.data.generator

import ru.flowernetes.entity.task.TaskInfo

interface PyTaskInfoDockerfileStringGenerator {
    fun gen(taskInfo: TaskInfo): String
}