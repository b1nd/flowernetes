package ru.flowernetes.entity.workload

import ru.flowernetes.entity.NOT_DEFINED_ID
import ru.flowernetes.entity.task.Task
import javax.persistence.*

@Entity
data class Workload(
  @Id @GeneratedValue
  val id: Long = NOT_DEFINED_ID,
  @ManyToOne
  val task: Task,
  val jobName: String,
  val baseImage: String,
  val memoryRequest: Long,
  val memoryLimit: Long,
  val cpuRequest: Double,
  val cpuLimit: Double,
  val sourceScriptId: String
)