package ru.flowernetes.workload.api.domain.entity

enum class LogMetadataKeys {
    FILENAME,
    WORKLOAD_ID,
    LOG;

    val key = "metadata.$name"
}