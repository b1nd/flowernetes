package ru.flowernetes.entity.team

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import ru.flowernetes.entity.NOT_DEFINED_ID
import ru.flowernetes.entity.auth.User
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
data class TeamUser(
  @Id @GeneratedValue
  val id: Long = NOT_DEFINED_ID,
  @OneToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  val team: Team,
  @OneToOne
  @OnDelete(action = OnDeleteAction.CASCADE)
  val user: User
)