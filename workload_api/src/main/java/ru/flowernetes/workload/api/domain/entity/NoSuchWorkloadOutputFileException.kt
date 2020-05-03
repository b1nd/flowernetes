package ru.flowernetes.workload.api.domain.entity

class NoSuchWorkloadOutputFileException(workloadId: Long)
    : NoSuchElementException("Output file not found for workload: $workloadId")