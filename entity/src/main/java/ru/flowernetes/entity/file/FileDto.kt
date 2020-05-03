package ru.flowernetes.entity.file

import java.io.InputStream

data class FileDto(
  val name: String,
  val contentType: String?,
  val inputStream: InputStream
)