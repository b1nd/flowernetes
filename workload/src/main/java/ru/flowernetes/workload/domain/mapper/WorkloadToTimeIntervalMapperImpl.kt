package ru.flowernetes.workload.domain.mapper

import org.springframework.stereotype.Component
import ru.flowernetes.entity.workload.TimeInterval
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.workload.api.domain.mapper.WorkloadToTimeIntervalMapper
import java.time.Instant
import java.time.ZoneId

@Component
class WorkloadToTimeIntervalMapperImpl : WorkloadToTimeIntervalMapper {
    override fun map(workload: Workload): TimeInterval {
        val startTime = workload.taskStartTime
          ?: workload.workloadCreationTime
        val endTime = workload.taskCompletionTime
          ?: Instant.ofEpochMilli(workload.lastTransitionTime).atZone(ZoneId.systemDefault()).toLocalDateTime()
        return TimeInterval(
          from = startTime,
          to = endTime
        )
    }
}