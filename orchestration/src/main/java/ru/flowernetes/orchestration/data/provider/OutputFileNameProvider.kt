package ru.flowernetes.orchestration.data.provider

import ru.flowernetes.entity.workload.Workload

interface OutputFileNameProvider {
    fun get(workload: Workload): String
}