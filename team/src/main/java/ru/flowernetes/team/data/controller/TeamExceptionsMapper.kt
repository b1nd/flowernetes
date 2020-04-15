package ru.flowernetes.team.data.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.flowernetes.orchestration.api.domain.entity.NoSuchNamespaceException
import ru.flowernetes.util.extensions.toResponseEntityStatus

@ControllerAdvice
class TeamExceptionsMapper {

    @ExceptionHandler
    fun noNamespace(e: NoSuchNamespaceException) = e.toResponseEntityStatus(HttpStatus.NOT_FOUND)
}