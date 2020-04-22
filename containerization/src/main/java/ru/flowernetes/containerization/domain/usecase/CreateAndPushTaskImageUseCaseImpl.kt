package ru.flowernetes.containerization.domain.usecase

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.command.BuildImageResultCallback
import com.github.dockerjava.api.model.Identifier
import com.github.dockerjava.api.model.Repository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.flowernetes.containerization.api.domain.usecase.CreateAndPushTaskImageUseCase
import ru.flowernetes.containerization.api.domain.usecase.CreateImageDirectoryUseCase
import ru.flowernetes.containerization.data.provider.TaskImageNameProvider
import ru.flowernetes.entity.task.Task

@Component
class CreateAndPushTaskImageUseCaseImpl(
  private val dockerClient: DockerClient,
  private val taskImageNameProvider: TaskImageNameProvider,
  private val createImageDirectoryUseCase: CreateImageDirectoryUseCase
) : CreateAndPushTaskImageUseCase {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun exec(task: Task): String {
        val imageDirectory = createImageDirectoryUseCase.exec(task).toFile()
        val imageName = taskImageNameProvider.get(task)

        log.info("Start building image for $task")

        val imageId = dockerClient
          .buildImageCmd(imageDirectory)
          .exec(BuildImageResultCallback())
          .awaitImageId()

        log.info("Image $imageId built for $task")

        imageDirectory.deleteRecursively()

        dockerClient.tagImageCmd(imageId, imageName.nameWithRepository, imageName.tag).exec()

        log.info("Image $imageId tagged ${imageName.fullName}")
        log.info("Pushing image ${imageName.fullName}")

        dockerClient
          .pushImageCmd(Identifier(Repository(imageName.nameWithRepository), imageName.tag))
          .start()
          .awaitCompletion()

        log.info("Image pushed ${imageName.fullName}")

        return imageName.fullName
    }
}
