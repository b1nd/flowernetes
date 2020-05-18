package ru.flowernetes.script.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.containerization.ScriptType
import ru.flowernetes.entity.script.SourceScript
import ru.flowernetes.script.api.domain.usecase.GetSourceScriptTypeByPathUseCase
import ru.flowernetes.script.api.domain.usecase.GetSourceScriptTypeUseCase

@Component
class GetSourceScriptTypeUseCaseImpl(
  private val getSourceScriptTypeByPathUseCase: GetSourceScriptTypeByPathUseCase
) : GetSourceScriptTypeUseCase {

    override fun exec(sourceScript: SourceScript): ScriptType {
        return getSourceScriptTypeByPathUseCase.exec(sourceScript.runFilePath)
    }
}