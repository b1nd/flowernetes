package ru.flowernetes.containerization.data.generator

import org.apache.commons.io.FilenameUtils
import org.springframework.stereotype.Component
import ru.flowernetes.containerization.api.domain.entity.*
import ru.flowernetes.entity.task.TaskInfo

@Component
class IpynbTaskInfoDockerfileStringGeneratorImpl : IpynbTaskInfoDockerfileStringGenerator {
    override fun gen(taskInfo: TaskInfo): String {
        val baseImage = taskInfo.task.baseImage
        val runFilePath = taskInfo.sourceScript.runFilePath
        val saveScript = taskInfo.task.saveScript
        val baseName = FilenameUtils.getBaseName(taskInfo.sourceScript.runFilePath)
        val outputName = "$DOCKERFILE_OUTPUT_FILE_PREFIX$baseName"

        return """
            FROM $baseImage
            USER $DOCKERFILE_USER
            COPY $DOCKERFILE_APP_DIR $DOCKERFILE_WORK_DIR/
            WORKDIR $DOCKERFILE_WORK_DIR
            CMD jupyter nbconvert --ExecutePreprocessor.timeout=-1 \
            --execute --to notebook --output $outputName \
            $runFilePath${if (saveScript) " && ${saveData("$outputName.ipynb")}" else ""}
        """.trimIndent()
    }

    private fun saveData(fileName: String) = """
        echo $DOCKERFILE_DATA_START && cat $fileName | base64 && echo $DOCKERFILE_DATA_END
    """.trimIndent()
}