package ru.flowernetes.orchestration.data.parser

import java.time.LocalDateTime

interface JobTimeParser {
    fun parse(timeString: String): LocalDateTime
}