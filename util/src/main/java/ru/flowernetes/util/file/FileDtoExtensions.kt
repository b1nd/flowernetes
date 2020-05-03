package ru.flowernetes.util.file

import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import ru.flowernetes.entity.file.FileDto

fun FileDto.toResponseEntity(): ResponseEntity<InputStreamResource> {
    val resource = InputStreamResource(inputStream)
    val contentType = contentType ?: MediaType.APPLICATION_OCTET_STREAM_VALUE

    return ResponseEntity.ok()
      .contentType(MediaType.parseMediaType(contentType))
      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=$name")
      .body(resource)
}