package ru.flowernetes.orchestration.domain.usecase

import org.apache.commons.codec.binary.Base64
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import ru.flowernetes.containerization.api.domain.entity.DOCKERFILE_DATA_END
import ru.flowernetes.containerization.api.domain.entity.DOCKERFILE_DATA_START
import ru.flowernetes.entity.file.FileDto
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.orchestration.api.domain.usecase.SaveLogAndDataFromLogReaderUseCase
import ru.flowernetes.orchestration.api.domain.usecase.SaveLogFromLogReaderUseCase
import ru.flowernetes.orchestration.data.provider.LogFileNameProvider
import ru.flowernetes.orchestration.data.provider.OutputFileNameProvider
import ru.flowernetes.workload.api.domain.usecase.AddWorkloadLogUseCase
import ru.flowernetes.workload.api.domain.usecase.AddWorkloadOutputFileUseCase
import java.io.File
import java.io.FileOutputStream
import java.io.Reader

@Component
class SaveLogAndDataFromLogReaderUseCaseImpl(
    private val saveLogFromLogReaderUseCase: SaveLogFromLogReaderUseCase,
    private val logFileNameProvider: LogFileNameProvider,
    private val outputFileNameProvider: OutputFileNameProvider,
    private val addWorkloadOutputFileUseCase: AddWorkloadOutputFileUseCase,
    private val addWorkloadLogUseCase: AddWorkloadLogUseCase
) : SaveLogAndDataFromLogReaderUseCase {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun exec(workload: Workload, reader: Reader) {
        log.debug("Saving log and data for $workload")

        // todo: workload saveScript & saveLog
        val hasData = workload.task.saveScript
        val saveLog = workload.task.saveLog

        if (!saveLog && !hasData) {
            log.debug("Nothing to save for $workload")
            return
        }
        if (saveLog && !hasData) {
            log.debug("Only log should be saved for $workload, using light log saver method")
            saveLogFromLogReaderUseCase.exec(workload, reader)
            return
        }

        val decoder = Base64()

        val logFileName = logFileNameProvider.get(workload)
        val outputFileName = outputFileNameProvider.get(workload)
        val log = if (saveLog) FileOutputStream(logFileName) else null
        val file = if (hasData) FileOutputStream(outputFileName) else null

        var isLog = true
        reader.forEachLine {
            if (it == DOCKERFILE_DATA_START) {
                isLog = false
                return@forEachLine
            }
            if (isLog) {
                log?.write("$it\n".toByteArray(Charsets.UTF_8))
            } else {
                if (it != DOCKERFILE_DATA_END) {
                    file?.write(decoder.decode(it))
                } else {
                    isLog = true
                    return@forEachLine
                }
            }
        }

        reader.close()
        log?.close()
        file?.close()

        log?.let {
            val logFile = File(logFileName)
            val logFileDto = FileDto(
              logFile.name,
              MediaType.TEXT_PLAIN_VALUE,
              logFile.inputStream()
            )
            addWorkloadLogUseCase.exec(workload, logFileDto)
            logFile.delete()
        }

        file?.let {
            val outputFile = File(outputFileName)
            val outputFileDto = FileDto(
              outputFile.name,
              MediaType.APPLICATION_OCTET_STREAM_VALUE,
              outputFile.inputStream()
            )
            addWorkloadOutputFileUseCase.exec(workload, outputFileDto)
            outputFile.delete()
        }
    }
}