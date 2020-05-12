package ru.flowernetes.workload.api.domain.mapper

import ru.flowernetes.entity.workload.TimeInterval
import ru.flowernetes.entity.workload.Workload

interface WorkloadToTimeIntervalMapper {
    fun map(workload: Workload): TimeInterval
}