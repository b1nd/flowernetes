package ru.flowernetes.orchestration.domain.usecase

import io.fabric8.kubernetes.client.KubernetesClient
import org.springframework.stereotype.Component
import ru.flowernetes.orchestration.api.domain.usecase.GetNamespaceNamesUseCase

@Component
class GetNamespaceNamesUseCaseImpl(
  private val kubernetesClient: KubernetesClient
) : GetNamespaceNamesUseCase {

    override fun exec(): List<String> {
        return kubernetesClient.namespaces().list().items
          .map { it.metadata.name }
    }
}