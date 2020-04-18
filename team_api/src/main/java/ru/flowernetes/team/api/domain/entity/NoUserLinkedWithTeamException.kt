package ru.flowernetes.team.api.domain.entity

import ru.flowernetes.entity.team.Team
import java.lang.IllegalArgumentException

class NoUserLinkedWithTeamException(team: Team) : IllegalArgumentException("There is no user for team $team")