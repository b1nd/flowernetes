package ru.flowernetes.auth.api.domain

import ru.flowernetes.entity.auth.SystemUserRole

fun RoleChecker.requireAll(vararg roles: SystemUserRole) {
    startCheck().require(*roles).requireAllSpecified()
}

fun RoleChecker.requireAny(vararg roles: SystemUserRole) {
    startCheck().require(*roles).requireAnySpecified()
}