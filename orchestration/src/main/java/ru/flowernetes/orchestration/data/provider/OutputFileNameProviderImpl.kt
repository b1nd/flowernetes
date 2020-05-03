package ru.flowernetes.orchestration.data.provider

import org.apache.commons.io.FilenameUtils
import org.springframework.stereotype.Component
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.script.api.domain.usecase.GetSourceScriptByIdUseCase
import ru.flowernetes.util.file.DATE_TIME_FILE_FORMAT_PATTERN
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Component
class OutputFileNameProviderImpl(
  private val getSourceScriptByIdUseCase: GetSourceScriptByIdUseCase
) : OutputFileNameProvider {

    private val formatter = DateTimeFormatter.ofPattern(DATE_TIME_FILE_FORMAT_PATTERN)

    override fun get(workload: Workload): String {
        val sourceScript = getSourceScriptByIdUseCase.exec(workload.sourceScriptId)
        val baseFileName = FilenameUtils.getBaseName(sourceScript.runFilePath)
        val extension = FilenameUtils.getExtension(sourceScript.runFilePath)
        val time = formatter.format(workload.taskCompletionTime ?: LocalDateTime.now())
        return "$baseFileName-$time.$extension"
    }
}