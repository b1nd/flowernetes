package ru.flowernetes.entity.workload

import ru.flowernetes.entity.NOT_DEFINED_ID
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.task.TaskStatus
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Workload(
  @Id @GeneratedValue
  val id: Long = NOT_DEFINED_ID,
  val workloadCreationTime: LocalDateTime,
  val lastTransitionTime: Long,
  @ManyToOne
  val task: Task,
  @Enumerated(EnumType.ORDINAL)
  val taskStatus: TaskStatus,
  val taskStartTime: LocalDateTime? = null,
  val taskCompletionTime: LocalDateTime? = null,
  val baseImage: String,
  val memoryRequest: Long,
  val memoryLimit: Long,
  val cpuRequest: Double,
  val cpuLimit: Double,
  val sourceScriptId: String
)