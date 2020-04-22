package ru.flowernetes.containerization.data.provider

import org.apache.commons.codec.digest.DigestUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import ru.flowernetes.entity.containerization.ImageName
import ru.flowernetes.entity.task.Task

@Component
class TaskImageNameProviderImpl(
  @Value("\${docker.registry.host}")
  private val dockerRegistry: String
) : TaskImageNameProvider {

    override fun get(task: Task): ImageName {
        val name = DigestUtils.sha1Hex(
          task.baseImage +
            task.sourceScriptId
        )
        val tag = task.id.toString()

        return ImageName(
          nameWithRepository = "$dockerRegistry/$name",
          tag = tag
        )
    }
}