package ru.flowernetes.orchestration.data.provider

import ru.flowernetes.entity.workload.Workload

interface LogFileNameProvider {
    fun get(workload: Workload): String
}