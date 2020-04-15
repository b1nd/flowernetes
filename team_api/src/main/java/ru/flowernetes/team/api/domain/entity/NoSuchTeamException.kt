package ru.flowernetes.team.api.domain.entity

class NoSuchTeamException(teamId: Long) : NoSuchElementException("There is no team with id: $teamId")