package ru.flowernetes.scheduling.domain.usecase

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Conditions
import ru.flowernetes.entity.task.Task
import ru.flowernetes.scheduling.api.domain.usecase.RemoveCronScheduleUseCase
import ru.flowernetes.scheduling.api.domain.usecase.RemoveTaskFromScheduleUseCase

@Component
class RemoveTaskFromScheduleUseCaseImpl(
  private val objectMapper: ObjectMapper,
  private val removeCronScheduleUseCase: RemoveCronScheduleUseCase
) : RemoveTaskFromScheduleUseCase {

    override fun exec(task: Task) {
        val conditions = objectMapper.readValue(task.conditionsJson, Conditions::class.java)
        conditions.timeCondition?.let {
            removeCronScheduleUseCase.exec(task)
        }
    }
}