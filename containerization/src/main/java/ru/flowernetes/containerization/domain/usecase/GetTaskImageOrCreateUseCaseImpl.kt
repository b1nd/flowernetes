package ru.flowernetes.containerization.domain.usecase

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.flowernetes.containerization.api.domain.entity.NoTaskPushedImageException
import ru.flowernetes.containerization.api.domain.usecase.CreateAndPushTaskImageUseCase
import ru.flowernetes.containerization.api.domain.usecase.GetTaskImageOrCreateUseCase
import ru.flowernetes.containerization.api.domain.usecase.GetTaskPushedImageUseCase
import ru.flowernetes.entity.task.Task

@Component
class GetTaskImageOrCreateUseCaseImpl(
  private val getTaskPushedImageUseCase: GetTaskPushedImageUseCase,
  private val createAndPushTaskImageUseCase: CreateAndPushTaskImageUseCase
) : GetTaskImageOrCreateUseCase {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun exec(task: Task): String = kotlin.runCatching {
        log.debug("Trying to get image for $task")
        val pushedImageName = getTaskPushedImageUseCase.exec(task)
        log.info("Image $pushedImageName already pushed for $task")
        pushedImageName
    }.getOrElse {
        when (it) {
            is NoTaskPushedImageException -> {
                log.debug("Pushed image not found for task $task")
                createAndPushTaskImageUseCase.exec(task)
            }
            else -> throw it
        }
    }
}