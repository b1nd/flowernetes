package ru.flowernetes.orchestration.data.config

import io.fabric8.kubernetes.client.DefaultKubernetesClient
import io.fabric8.kubernetes.client.KubernetesClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class KubernetesClientConfig(
  @Value("\${kubernetes.host}")
  val masterUrl: String
) {

    @Bean
    open fun kubernetesClient(): KubernetesClient {
        return DefaultKubernetesClient(masterUrl)
    }
}