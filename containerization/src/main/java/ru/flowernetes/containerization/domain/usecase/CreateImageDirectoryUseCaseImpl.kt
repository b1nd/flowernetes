package ru.flowernetes.containerization.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.containerization.api.domain.usecase.CreateImageDirectoryUseCase
import ru.flowernetes.containerization.data.generator.TaskInfoDockerfileGenerator
import ru.flowernetes.entity.task.Task
import ru.flowernetes.entity.task.TaskInfo
import ru.flowernetes.script.api.domain.usecase.GetSourceScriptByIdUseCase
import ru.flowernetes.script.api.domain.usecase.GetSourceScriptFileDtoByIdUseCase
import ru.flowernetes.util.zip.ZipUtil
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

@Component
class CreateImageDirectoryUseCaseImpl(
  private val getSourceScriptFileDtoByIdUseCase: GetSourceScriptFileDtoByIdUseCase,
  private val taskInfoDockerfileGenerator: TaskInfoDockerfileGenerator,
  private val getSourceScriptByIdUseCase: GetSourceScriptByIdUseCase
) : CreateImageDirectoryUseCase {

    override fun exec(task: Task): Path {
        val scriptDir = getSourceScriptFileDtoByIdUseCase.exec(task.sourceScriptId)
        val sourceScript = getSourceScriptByIdUseCase.exec(task.sourceScriptId)

        val imageDirectory = Files.createTempDirectory("docker")
        val appDir = File(imageDirectory.toFile(), "app")
        appDir.mkdir()

        taskInfoDockerfileGenerator.gen(TaskInfo(task, sourceScript), imageDirectory)
        ZipUtil.unzip(scriptDir.inputStream, appDir)

        return imageDirectory
    }
}