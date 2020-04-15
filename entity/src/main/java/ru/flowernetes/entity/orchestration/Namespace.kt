package ru.flowernetes.entity.orchestration

data class Namespace(
  val name: String,
  val resourceQuota: ResourceQuota
)