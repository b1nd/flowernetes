package ru.flowernetes.scheduling.domain.usecase

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Conditions
import ru.flowernetes.entity.task.Task
import ru.flowernetes.scheduling.api.domain.usecase.ScheduleCronTaskUseCase
import ru.flowernetes.scheduling.api.domain.usecase.ScheduleTaskUseCase

@Component
class ScheduleTaskUseCaseImpl(
  private val objectMapper: ObjectMapper,
  private val scheduleCronTaskUseCase: ScheduleCronTaskUseCase
) : ScheduleTaskUseCase {

    override fun exec(task: Task) {
        val conditions = objectMapper.readValue(task.conditionsJson, Conditions::class.java)

        conditions.timeCondition?.let {
            scheduleCronTaskUseCase.exec(task, it)
        }
    }
}