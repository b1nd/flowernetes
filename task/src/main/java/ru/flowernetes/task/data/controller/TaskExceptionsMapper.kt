package ru.flowernetes.task.data.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.flowernetes.task.api.domain.entity.TaskControlNotAllowedException
import ru.flowernetes.util.extensions.toResponseEntityStatus

@ControllerAdvice
class TaskExceptionsMapper {

    @ExceptionHandler
    fun notAllowed(e: TaskControlNotAllowedException) = e.toResponseEntityStatus(HttpStatus.METHOD_NOT_ALLOWED)
}