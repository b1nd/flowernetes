package ru.flowernetes.util.extensions

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun LocalDateTime.toMilli(): Long {
    return atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

fun milliToTime(milli: Long): LocalDateTime {
    return Instant.ofEpochMilli(milli).atZone(ZoneId.systemDefault()).toLocalDateTime()
}