package ru.flowernetes.script.api.domain.usecase

import ru.flowernetes.script.api.domain.dto.FileDto

interface GetSourceScriptByIdUseCase {
    fun exec(id: String): FileDto
}