package ru.flowernetes.script.api.domain.dto

data class SourceScriptDto(
  val name: String,
  val filename: String,
  val tag: String,
  val runFilePath: String,
  val isPublic: Boolean
)