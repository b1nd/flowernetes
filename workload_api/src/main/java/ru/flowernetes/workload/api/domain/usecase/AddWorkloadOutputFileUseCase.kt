package ru.flowernetes.workload.api.domain.usecase

import ru.flowernetes.entity.file.FileDto
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.entity.workload.WorkloadOutputFile

interface AddWorkloadOutputFileUseCase {
    fun exec(workload: Workload, fileDto: FileDto): WorkloadOutputFile
}