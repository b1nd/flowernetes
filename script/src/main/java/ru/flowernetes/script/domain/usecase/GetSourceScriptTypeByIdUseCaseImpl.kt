package ru.flowernetes.script.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.containerization.ScriptType
import ru.flowernetes.script.api.domain.usecase.GetSourceScriptByIdUseCase
import ru.flowernetes.script.api.domain.usecase.GetSourceScriptTypeByIdUseCase
import ru.flowernetes.script.api.domain.usecase.GetSourceScriptTypeUseCase

@Component
class GetSourceScriptTypeByIdUseCaseImpl(
  private val getSourceScriptByIdUseCase: GetSourceScriptByIdUseCase,
  private val getSourceScriptTypeUseCase: GetSourceScriptTypeUseCase
) : GetSourceScriptTypeByIdUseCase {

    override fun exec(sourceScriptId: String): ScriptType {
        return getSourceScriptByIdUseCase.exec(sourceScriptId)
          .let(getSourceScriptTypeUseCase::exec)
    }
}