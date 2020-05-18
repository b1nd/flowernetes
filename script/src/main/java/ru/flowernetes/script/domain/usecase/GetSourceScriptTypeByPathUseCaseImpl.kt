package ru.flowernetes.script.domain.usecase

import org.apache.commons.io.FilenameUtils
import org.springframework.stereotype.Component
import ru.flowernetes.entity.containerization.ScriptType
import ru.flowernetes.script.api.domain.entity.UnsupportedScriptExtension
import ru.flowernetes.script.api.domain.usecase.GetSourceScriptTypeByPathUseCase

@Component
class GetSourceScriptTypeByPathUseCaseImpl : GetSourceScriptTypeByPathUseCase {
    override fun exec(path: String): ScriptType {
        return when (FilenameUtils.getExtension(path).toLowerCase()) {
            "py" -> ScriptType.PY
            "ipynb" -> ScriptType.IPYNB
            else -> throw UnsupportedScriptExtension(path)
        }
    }
}