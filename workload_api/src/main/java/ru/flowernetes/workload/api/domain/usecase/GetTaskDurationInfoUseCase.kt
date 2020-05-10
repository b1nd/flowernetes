package ru.flowernetes.workload.api.domain.usecase

import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workload.TaskDuration
import ru.flowernetes.entity.workload.TaskDurationFilter

interface GetTaskDurationInfoUseCase {
    fun exec(task: Task, taskDurationFilter: TaskDurationFilter): TaskDuration?
}