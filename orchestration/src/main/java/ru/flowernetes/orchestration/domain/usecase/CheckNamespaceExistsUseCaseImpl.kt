package ru.flowernetes.orchestration.domain.usecase

import io.fabric8.kubernetes.client.KubernetesClient
import org.springframework.stereotype.Component
import ru.flowernetes.orchestration.api.domain.entity.NoSuchNamespaceException
import ru.flowernetes.orchestration.api.domain.usecase.CheckNamespaceExistsUseCase

@Component
class CheckNamespaceExistsUseCaseImpl(
  private val kubernetesClient: KubernetesClient
) : CheckNamespaceExistsUseCase {

    override fun exec(namespaceName: String) {
        kubernetesClient.namespaces().withName(namespaceName).get() ?: throw NoSuchNamespaceException(namespaceName)
    }
}