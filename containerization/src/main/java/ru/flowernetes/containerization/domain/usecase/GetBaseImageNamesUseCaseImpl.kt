package ru.flowernetes.containerization.domain.usecase

import com.github.dockerjava.api.DockerClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import ru.flowernetes.containerization.api.domain.usecase.GetBaseImageNamesUseCase

@Component
class GetBaseImageNamesUseCaseImpl(
  private val dockerClient: DockerClient,
  @Value("\${docker.registry.host}")
  private val dockerRegistry: String
) : GetBaseImageNamesUseCase {

    override fun exec(): List<String> {
        return dockerClient
          .listImagesCmd().exec()
          .filterNot { image -> image.repoTags == null || image.repoTags.isEmpty() || image.repoTags.any { it.startsWith(dockerRegistry) } }
          .map { it.repoTags[0] }
    }
}