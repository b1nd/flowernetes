package ru.flowernetes.entity.task

import ru.flowernetes.entity.NOT_DEFINED_ID
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class DependencyMarker(
  @Id @GeneratedValue
  val id: Long = NOT_DEFINED_ID,
  @ManyToOne
  val task: Task,
  @ManyToOne
  val dependencyTask: Task,
  val marker: Boolean
)