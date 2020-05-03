package ru.flowernetes.orchestration.api.domain.usecase

import ru.flowernetes.entity.workload.Workload
import java.io.Reader

interface SaveLogFromLogReaderUseCase {
    fun exec(workload: Workload, reader: Reader)
}