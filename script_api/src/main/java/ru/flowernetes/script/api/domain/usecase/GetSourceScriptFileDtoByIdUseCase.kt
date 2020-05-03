package ru.flowernetes.script.api.domain.usecase

import ru.flowernetes.entity.file.FileDto

interface GetSourceScriptFileDtoByIdUseCase {
    fun exec(id: String): FileDto
}