package ru.flowernetes.scheduling.data.service

import org.springframework.scheduling.config.CronTask
import org.springframework.scheduling.config.ScheduledTask
import org.springframework.scheduling.config.ScheduledTaskRegistrar
import org.springframework.scheduling.support.CronTrigger
import org.springframework.stereotype.Service
import ru.flowernetes.entity.task.Task
import ru.flowernetes.orchestration.api.domain.usecase.RunTaskUseCase

@Service
class CronService(
  private val scheduledTaskRegistrar: ScheduledTaskRegistrar,
  private val runTaskUseCase: RunTaskUseCase
) {
    private val taskIdToScheduledTask = mutableMapOf<Long, ScheduledTask>()

    fun addCronTask(task: Task, cronExpression: String) {
        val scheduledTask = scheduledTaskRegistrar.scheduleCronTask(CronTask(
          { runTaskUseCase.execAsync(task) }, CronTrigger(cronExpression)
        ))
        scheduledTask?.let {
            taskIdToScheduledTask.put(task.id, it)
        }
    }

    fun removeCronTask(task: Task) {
        taskIdToScheduledTask[task.id]?.let {
            it.cancel()
            taskIdToScheduledTask.remove(task.id)
        }
    }
}