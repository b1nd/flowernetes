package ru.flowernetes.orchestration.domain.usecase

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.flowernetes.entity.orchestration.Namespace
import ru.flowernetes.entity.orchestration.ResourceQuota
import ru.flowernetes.orchestration.api.domain.usecase.GetNamespaceByNameOrEmptyUseCase
import ru.flowernetes.orchestration.api.domain.usecase.GetNamespaceByNameUseCase

@Component
class GetNamespaceByNameOrEmptyUseCaseImpl(
  private val getNamespaceByNameUseCase: GetNamespaceByNameUseCase
) : GetNamespaceByNameOrEmptyUseCase {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun exec(namespaceName: String): Namespace {
        return runCatching { getNamespaceByNameUseCase.exec(namespaceName) }.getOrElse { e ->
            log.error(e.message, e)
            Namespace(
              "$namespaceName not found!",
              ResourceQuota(0, 0, 0.0, 0.0)
            )
        }
    }
}