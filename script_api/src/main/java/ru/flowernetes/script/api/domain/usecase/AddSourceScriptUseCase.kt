package ru.flowernetes.script.api.domain.usecase

import ru.flowernetes.entity.script.SourceScript
import ru.flowernetes.script.api.domain.dto.FileDto
import ru.flowernetes.script.api.domain.dto.SourceScriptDto

interface AddSourceScriptUseCase {
    fun exec(sourceScriptDto: SourceScriptDto, fileDto: FileDto): SourceScript
}