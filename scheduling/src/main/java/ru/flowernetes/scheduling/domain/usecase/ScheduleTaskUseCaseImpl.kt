package ru.flowernetes.scheduling.domain.usecase

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Condition
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.task.TimeCondition
import ru.flowernetes.scheduling.api.domain.usecase.ScheduleCronTaskUseCase
import ru.flowernetes.scheduling.api.domain.usecase.ScheduleTaskUseCase

@Component
class ScheduleTaskUseCaseImpl(
  private val objectMapper: ObjectMapper,
  private val scheduleCronTaskUseCase: ScheduleCronTaskUseCase
) : ScheduleTaskUseCase {

    override fun exec(task: Task) {
        when (val condition = objectMapper.readValue(task.conditionJson, Condition::class.java)) {
            is TimeCondition -> scheduleCronTaskUseCase.exec(task, condition)
        }
    }
}