package ru.flowernetes.entity.team

import ru.flowernetes.entity.NOT_DEFINED_ID
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Team(
  @Id @GeneratedValue
  val id: Long = NOT_DEFINED_ID,
  val name: String,
  val namespace: String
)