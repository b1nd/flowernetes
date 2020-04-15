package ru.flowernetes.entity.orchestration

data class ResourceQuota(
  // in bytes
  val memoryRequest: Long,
  // in bytes
  val memoryLimit: Long,
  val cpuRequest: Double,
  val cpuLimit: Double
)