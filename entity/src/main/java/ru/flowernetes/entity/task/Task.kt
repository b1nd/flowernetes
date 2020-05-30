package ru.flowernetes.entity.task

import ru.flowernetes.entity.NOT_DEFINED_ID
import ru.flowernetes.entity.workflow.Workflow
import javax.persistence.*

@Entity
data class Task(
  @Id @GeneratedValue
  val id: Long = NOT_DEFINED_ID,
  val name: String,
  @ManyToOne
  val workflow: Workflow,
  @Column(columnDefinition = "TEXT")
  val conditionsJson: String,
  val scheduled: Boolean,
  val baseImage: String,
  val timeDeadline: Long?,
  val maxRetries: Int,
  val memoryRequest: Long,
  val memoryLimit: Long,
  val cpuRequest: Double,
  val cpuLimit: Double,
  val saveLog: Boolean,
  val saveScript: Boolean,
  val sourceScriptId: String
) {
    fun workflowWithName(): String {
        return "${workflow.name}/$name"
    }
}