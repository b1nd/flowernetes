package ru.flowernetes.orchestration.domain.usecase

import org.springframework.stereotype.Component
import ru.flowernetes.entity.orchestration.Namespace
import ru.flowernetes.orchestration.api.domain.usecase.GetNamespaceByNameUseCase
import ru.flowernetes.orchestration.api.domain.usecase.GetNamespaceNamesUseCase
import ru.flowernetes.orchestration.api.domain.usecase.GetNamespacesUseCase

@Component
class GetNamespacesUseCaseImpl(
  private val getNamespaceByNameUseCase: GetNamespaceByNameUseCase,
  private val getNamespaceNamesUseCase: GetNamespaceNamesUseCase
) : GetNamespacesUseCase {

    override fun exec(): List<Namespace> {
        return getNamespaceNamesUseCase.exec()
          .map { getNamespaceByNameUseCase.exec(it) }
    }
}