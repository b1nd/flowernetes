package ru.flowernetes.containerization.data.generator

import org.apache.commons.io.FileUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.flowernetes.containerization.api.domain.entity.DOCKERFILE_NAME
import ru.flowernetes.entity.containerization.ScriptType
import ru.flowernetes.entity.task.TaskInfo
import ru.flowernetes.script.api.domain.usecase.GetSourceScriptTypeUseCase
import java.io.File
import java.nio.file.Path

@Component
class TaskInfoDockerfileGeneratorImpl(
  private val getSourceScriptTypeUseCase: GetSourceScriptTypeUseCase,
  private val pyTaskInfoDockerfileStringGenerator: PyTaskInfoDockerfileStringGenerator,
  private val ipynbTaskInfoDockerfileStringGenerator: IpynbTaskInfoDockerfileStringGenerator
) : TaskInfoDockerfileGenerator {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun gen(taskInfo: TaskInfo, destDir: Path) {
        val dockerfileContent = when (getSourceScriptTypeUseCase.exec(taskInfo.sourceScript)) {
            ScriptType.PY -> pyTaskInfoDockerfileStringGenerator.gen(taskInfo)
            ScriptType.IPYNB -> ipynbTaskInfoDockerfileStringGenerator.gen(taskInfo)
        }
        log.debug("Generated Dockerfile for $taskInfo\n$dockerfileContent")
        FileUtils.writeStringToFile(File(destDir.toFile(), DOCKERFILE_NAME), dockerfileContent, Charsets.UTF_8)
    }
}