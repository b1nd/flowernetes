package ru.flowernetes.orchestration.domain.usecase

import io.fabric8.kubernetes.api.model.Quantity
import io.fabric8.kubernetes.client.KubernetesClient
import org.springframework.stereotype.Component
import ru.flowernetes.entity.orchestration.ResourceQuota
import ru.flowernetes.orchestration.api.domain.usecase.GetNamespaceResourceQuotaUseCase
import ru.flowernetes.orchestration.CPU_LIMITS
import ru.flowernetes.orchestration.CPU_REQUESTS
import ru.flowernetes.orchestration.MEMORY_LIMITS
import ru.flowernetes.orchestration.MEMORY_REQUESTS

@Component
class GetNamespaceResourceQuotaUseCaseImpl(
  private val kubernetesClient: KubernetesClient
) : GetNamespaceResourceQuotaUseCase {

    override fun exec(namespaceName: String): ResourceQuota {
        var resourceQuota = ResourceQuota(0, 0, 0.0, 0.0)

        val resourceQuotas = kubernetesClient
          .resourceQuotas()
          .inNamespace(namespaceName)
          .list().items
          .flatMap { it.spec.hard.entries }

        resourceQuotas.forEach {
            when (it.key) {
                CPU_REQUESTS -> resourceQuota = resourceQuota.copy(
                  cpuRequest = getDoubleFromQuantity(it.value)
                )
                CPU_LIMITS -> resourceQuota = resourceQuota.copy(
                  cpuLimit = getDoubleFromQuantity(it.value)
                )
                MEMORY_REQUESTS -> resourceQuota = resourceQuota.copy(
                  memoryRequest = getLongFromQuantity(it.value)
                )
                MEMORY_LIMITS -> resourceQuota = resourceQuota.copy(
                  memoryLimit = getLongFromQuantity(it.value)
                )
            }
        }
        return resourceQuota
    }

    private fun getLongFromQuantity(quantity: Quantity) = Quantity.getAmountInBytes(quantity).longValueExact()

    private fun getDoubleFromQuantity(quantity: Quantity) = Quantity.getAmountInBytes(quantity).toDouble()
}