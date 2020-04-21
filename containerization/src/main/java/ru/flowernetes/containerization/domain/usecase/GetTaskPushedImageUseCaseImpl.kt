package ru.flowernetes.containerization.domain.usecase

import com.github.dockerjava.api.DockerClient
import org.springframework.stereotype.Component
import ru.flowernetes.containerization.api.domain.entity.NoTaskPushedImageException
import ru.flowernetes.containerization.api.domain.usecase.GetTaskPushedImageUseCase
import ru.flowernetes.containerization.data.provider.TaskImageNameProvider
import ru.flowernetes.entity.task.Task

@Component
class GetTaskPushedImageUseCaseImpl(
  private val dockerClient: DockerClient,
  private val taskImageNameProvider: TaskImageNameProvider
) : GetTaskPushedImageUseCase {

    override fun exec(task: Task): String {
        val fullImageName = taskImageNameProvider.get(task).fullName
        val images = dockerClient
          .listImagesCmd()
          .withImageNameFilter(fullImageName)
          .exec()

        if (images.isEmpty()) {
            throw NoTaskPushedImageException(task)
        } else {
            return fullImageName
        }
    }
}