package ru.flowernetes.entity.script

import java.time.LocalDateTime

data class SourceScript(
  val id: String,
  val name: String,
  val filename: String,
  val tag: String,
  val runFilePath: String,
  val uploadDate: LocalDateTime,
  val teamId: Long,
  val isPublic: Boolean
)