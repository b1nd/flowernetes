package ru.flowernetes.containerization.data.config

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.core.DefaultDockerClientConfig
import com.github.dockerjava.core.DockerClientBuilder
import com.github.dockerjava.core.DockerClientConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class DockerClientConfiguration(
  @Value("\${docker.host}")
  private val dockerHost: String,
  @Value("\${docker.version}")
  private val dockerApiVersion: String,
  @Value("\${docker.registry.protocol}")
  private val dockerRegistryProtocol: String,
  @Value("\${docker.registry.host}")
  private val dockerRegistryHost: String,
  @Value("\${docker.registry.version}")
  private val dockerRegistryVersion: String
) {

    @Bean
    open fun dockerClient(): DockerClient {
        val config: DockerClientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
          .withDockerHost(dockerHost)
          .withApiVersion(dockerApiVersion)
          .withRegistryUrl("$dockerRegistryProtocol://$dockerRegistryHost/$dockerRegistryVersion/")
          .build()

        return DockerClientBuilder.getInstance(config).build()
    }
}