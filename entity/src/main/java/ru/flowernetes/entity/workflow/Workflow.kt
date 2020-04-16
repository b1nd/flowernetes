package ru.flowernetes.entity.workflow

import ru.flowernetes.entity.NOT_DEFINED_ID
import ru.flowernetes.entity.team.Team
import javax.persistence.*

@Entity
data class Workflow(
  @Id @GeneratedValue
  val id: Long = NOT_DEFINED_ID,
  val name: String,
  val isPublic: Boolean,
  @ManyToOne
  val team: Team
)