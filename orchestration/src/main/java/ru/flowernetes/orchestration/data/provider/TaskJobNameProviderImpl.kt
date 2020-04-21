package ru.flowernetes.orchestration.data.provider

import org.apache.commons.codec.digest.DigestUtils
import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task

@Component
class TaskJobNameProviderImpl : TaskJobNameProvider {
    override fun get(task: Task): String {
        return DigestUtils.sha1Hex(task.toString())
    }
}