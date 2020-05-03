package ru.flowernetes.workload.api.domain.usecase

import ru.flowernetes.entity.file.FileDto

interface GetWorkloadOutputFileDtoByWorkloadIdUseCase {
    fun exec(workloadId: Long): FileDto
}