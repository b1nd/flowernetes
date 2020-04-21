package ru.flowernetes.orchestration.data.provider

import ru.flowernetes.entity.task.Task

interface TaskJobNameProvider {
    fun get(task: Task): String
}