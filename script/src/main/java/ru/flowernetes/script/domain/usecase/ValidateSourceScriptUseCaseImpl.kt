package ru.flowernetes.script.domain.usecase

import org.apache.commons.io.FilenameUtils
import org.springframework.stereotype.Component
import ru.flowernetes.script.api.domain.dto.SourceScriptDto
import ru.flowernetes.script.api.domain.entity.SourceScriptValidationException
import ru.flowernetes.script.api.domain.usecase.GetSourceScriptTypeByPathUseCase
import ru.flowernetes.script.api.domain.usecase.ValidateSourceScriptUseCase
import java.nio.file.Paths

@Component
class ValidateSourceScriptUseCaseImpl(
  private val getSourceScriptTypeByPathUseCase: GetSourceScriptTypeByPathUseCase
) : ValidateSourceScriptUseCase {

    override fun exec(sourceScript: SourceScriptDto) {
        checkSourceScriptExtension(sourceScript)
        checkRunFilePath(sourceScript)
    }

    private fun checkSourceScriptExtension(sourceScript: SourceScriptDto) {
        val extension = FilenameUtils.getExtension(sourceScript.filename)
        if (!extension.equals("zip", true)) {
            throw SourceScriptValidationException("Source script should be a zip archive!")
        }
    }

    private fun checkRunFilePath(sourceScript: SourceScriptDto) {
        Paths.get(sourceScript.runFilePath)
        getSourceScriptTypeByPathUseCase.exec(sourceScript.runFilePath)
    }
}