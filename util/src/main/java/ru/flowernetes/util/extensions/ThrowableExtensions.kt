package ru.flowernetes.util.extensions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

fun Throwable.toResponseEntityStatus(status: HttpStatus, message: String? = null): ResponseEntity<String> {
    return ResponseEntity(message ?: this.message.orEmpty(), status)
}