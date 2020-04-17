package ru.flowernetes.script.api.domain.dto

import java.io.InputStream

data class FileDto(
  val name: String,
  val contentType: String?,
  val inputStream: InputStream
)