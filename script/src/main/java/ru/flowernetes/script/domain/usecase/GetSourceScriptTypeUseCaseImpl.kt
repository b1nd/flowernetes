package ru.flowernetes.script.domain.usecase

import org.apache.commons.io.FilenameUtils
import org.springframework.stereotype.Component
import ru.flowernetes.entity.containerization.ScriptType
import ru.flowernetes.entity.script.SourceScript
import ru.flowernetes.script.api.domain.entity.UnsupportedScriptExtension
import ru.flowernetes.script.api.domain.usecase.GetSourceScriptTypeUseCase

@Component
class GetSourceScriptTypeUseCaseImpl : GetSourceScriptTypeUseCase {
    override fun exec(sourceScript: SourceScript): ScriptType {
        return when (FilenameUtils.getExtension(sourceScript.runFilePath).toLowerCase()) {
            "py" -> ScriptType.PY
            "ipynb" -> ScriptType.IPYNB
            else -> throw UnsupportedScriptExtension(sourceScript)
        }
    }
}