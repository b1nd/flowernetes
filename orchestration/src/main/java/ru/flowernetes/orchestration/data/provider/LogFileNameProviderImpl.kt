package ru.flowernetes.orchestration.data.provider

import org.springframework.stereotype.Component
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.util.file.DATE_TIME_FILE_FORMAT_PATTERN
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class LogFileNameProviderImpl : LogFileNameProvider {

    private val formatter = DateTimeFormatter.ofPattern(DATE_TIME_FILE_FORMAT_PATTERN)

    override fun get(workload: Workload): String {
        val baseFileName = workload.id
        val time = formatter.format(workload.taskCompletionTime ?: LocalDateTime.now())
        return "$baseFileName-$time.log"
    }
}