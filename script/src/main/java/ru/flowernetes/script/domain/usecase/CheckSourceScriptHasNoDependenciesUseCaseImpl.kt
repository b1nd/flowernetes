package ru.flowernetes.script.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.script.api.domain.usecase.CheckSourceScriptHasNoDependenciesUseCase
import ru.flowernetes.task.api.domain.usecase.GetTasksBySourceScriptIdUseCase

@Component
class CheckSourceScriptHasNoDependenciesUseCaseImpl(
  private val getTasksBySourceScriptIdUseCase: GetTasksBySourceScriptIdUseCase
) : CheckSourceScriptHasNoDependenciesUseCase {

    override fun exec(sourceScriptId: String) {
        val dependentTasks = getTasksBySourceScriptIdUseCase.exec(sourceScriptId)
        if (dependentTasks.isNotEmpty()) {
            val tasksMsg = dependentTasks.joinToString { it.name }
            throw IllegalStateException("Source script with id $sourceScriptId has task dependencies: $tasksMsg")
        }
    }
}