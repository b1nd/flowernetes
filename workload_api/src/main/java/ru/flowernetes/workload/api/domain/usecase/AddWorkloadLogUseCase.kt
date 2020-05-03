package ru.flowernetes.workload.api.domain.usecase

import ru.flowernetes.entity.file.FileDto
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.entity.workload.WorkloadLog

interface AddWorkloadLogUseCase {
    fun exec(workload: Workload, fileDto: FileDto): WorkloadLog
}