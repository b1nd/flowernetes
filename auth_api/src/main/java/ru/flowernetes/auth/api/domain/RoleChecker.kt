package ru.flowernetes.auth.api.domain

import ru.flowernetes.entity.auth.SystemUserRole

interface RoleChecker {

    interface Setup {
        fun require(vararg roles: SystemUserRole): Setup
        fun requireAnySpecified()
        fun requireAllSpecified()
    }

    fun startCheck(): Setup
}