package ru.flowernetes.containerization.data.generator

import ru.flowernetes.entity.task.TaskInfo

interface IpynbTaskInfoDockerfileStringGenerator {
    fun gen(taskInfo: TaskInfo): String
}