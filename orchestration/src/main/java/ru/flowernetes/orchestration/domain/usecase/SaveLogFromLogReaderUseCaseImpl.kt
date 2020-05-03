package ru.flowernetes.orchestration.domain.usecase

import org.apache.commons.io.input.ReaderInputStream
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import ru.flowernetes.entity.file.FileDto
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.orchestration.api.domain.usecase.SaveLogFromLogReaderUseCase
import ru.flowernetes.orchestration.data.provider.LogFileNameProvider
import ru.flowernetes.workload.api.domain.usecase.AddWorkloadLogUseCase
import java.io.Reader

@Component
class SaveLogFromLogReaderUseCaseImpl(
  private val logFileNameProvider: LogFileNameProvider,
  private val addWorkloadLogUseCase: AddWorkloadLogUseCase
) : SaveLogFromLogReaderUseCase {

    override fun exec(workload: Workload, reader: Reader) {
        val inputStream = ReaderInputStream(reader, Charsets.UTF_8)
        val logFileDto = FileDto(
          logFileNameProvider.get(workload),
          MediaType.TEXT_PLAIN_VALUE,
          inputStream
        )
        addWorkloadLogUseCase.exec(workload, logFileDto)
    }
}