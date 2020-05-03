package ru.flowernetes.workload.api.domain.entity

enum class OutputFileMetadataKeys {
    WORKLOAD_ID,
    FILENAME,
    OUTPUT;

    val key = "metadata.$name"
}