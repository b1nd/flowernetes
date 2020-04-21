package ru.flowernetes.containerization.data.provider

import ru.flowernetes.entity.containerization.ImageName
import ru.flowernetes.entity.task.Task

interface TaskImageNameProvider {
    fun get(task: Task): ImageName
}