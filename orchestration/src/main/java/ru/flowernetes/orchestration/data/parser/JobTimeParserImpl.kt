package ru.flowernetes.orchestration.data.parser

import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime


@Component
class JobTimeParserImpl : JobTimeParser {

    override fun parse(timeString: String): LocalDateTime {
        return ZonedDateTime.parse(timeString)
          .withZoneSameInstant(ZoneId.systemDefault())
          .toLocalDateTime()
    }
}