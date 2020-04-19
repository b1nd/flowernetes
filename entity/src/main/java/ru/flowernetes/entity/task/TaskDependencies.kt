package ru.flowernetes.entity.task

import ru.flowernetes.entity.NOT_DEFINED_ID
import javax.persistence.*

@Entity
data class TaskDependencies(
  @Id @GeneratedValue
  val id: Long = NOT_DEFINED_ID,
  @OneToOne
  val task: Task,
  @OneToMany
  val dependencyMarkers: List<DependencyMarker>
)