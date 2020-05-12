package ru.flowernetes.workload.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.workload.TimeInterval
import ru.flowernetes.entity.workload.TimeIntervalWithSeconds
import ru.flowernetes.workload.api.domain.usecase.GetTaskTimeIntervalsBetweenDatesUseCase
import ru.flowernetes.workload.api.domain.usecase.GetTaskTimeIntervalsWithSecondsByDateUseCase
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Component
class GetTaskTimeIntervalsWithSecondsByDateUseCaseImpl(
  private val getTaskTimeIntervalsBetweenDatesUseCase: GetTaskTimeIntervalsBetweenDatesUseCase
) : GetTaskTimeIntervalsWithSecondsByDateUseCase {

    override fun exec(task: Task, date: LocalDate): List<TimeIntervalWithSeconds> {
        val intervals = getTaskTimeIntervalsBetweenDatesUseCase.exec(task, date, date)
        return intervals.map(::timeIntervalWithSeconds)
    }

    private fun timeIntervalWithSeconds(timeInterval: TimeInterval): TimeIntervalWithSeconds {
        return TimeIntervalWithSeconds(
          from = timeInterval.from,
          to = timeInterval.to,
          seconds = ChronoUnit.SECONDS.between(timeInterval.from, timeInterval.to)
        )
    }
}