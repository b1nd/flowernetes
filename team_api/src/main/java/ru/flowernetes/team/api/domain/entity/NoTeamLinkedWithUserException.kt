package ru.flowernetes.team.api.domain.entity

import ru.flowernetes.entity.auth.User

class NoTeamLinkedWithUserException(user: User) : Throwable("There is no team for user $user")