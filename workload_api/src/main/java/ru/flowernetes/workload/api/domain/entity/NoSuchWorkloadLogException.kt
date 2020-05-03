package ru.flowernetes.workload.api.domain.entity

class NoSuchWorkloadLogException(workloadId: Long)
    : NoSuchElementException("Log not found for workload: $workloadId")