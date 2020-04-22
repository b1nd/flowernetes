package ru.flowernetes.team.api.domain.entity

import ru.flowernetes.entity.team.Team

class RequiredCallingTeamException(team: Team) : IllegalArgumentException("Only $team can do this action")