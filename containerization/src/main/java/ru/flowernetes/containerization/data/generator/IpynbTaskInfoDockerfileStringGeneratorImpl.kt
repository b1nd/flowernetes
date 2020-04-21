package ru.flowernetes.containerization.data.generator

import org.springframework.stereotype.Component
import ru.flowernetes.containerization.api.domain.entity.DOCKERFILE_APP_DIR
import ru.flowernetes.containerization.api.domain.entity.DOCKERFILE_USER
import ru.flowernetes.containerization.api.domain.entity.DOCKERFILE_WORK_DIR
import ru.flowernetes.entity.task.TaskInfo

@Component
class IpynbTaskInfoDockerfileStringGeneratorImpl : IpynbTaskInfoDockerfileStringGenerator {
    override fun gen(taskInfo: TaskInfo): String = """
            FROM ${taskInfo.task.baseImage}
            USER $DOCKERFILE_USER
            COPY $DOCKERFILE_APP_DIR $DOCKERFILE_WORK_DIR/
            WORKDIR $DOCKERFILE_WORK_DIR
            CMD jupyter nbconvert --ExecutePreprocessor.timeout=-1 --execute --to notebook \
            ${taskInfo.sourceScript.runFilePath}
        """.trimIndent()
}