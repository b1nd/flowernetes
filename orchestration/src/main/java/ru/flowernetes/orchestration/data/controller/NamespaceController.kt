package ru.flowernetes.orchestration.data.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.flowernetes.orchestration.api.domain.usecase.GetNamespaceNamesUseCase
import ru.flowernetes.orchestration.data.dto.NamespaceNamesDto

@RestController
@RequestMapping("/namespaces")
class NamespaceController(
  private val getNamespaceNamesUseCase: GetNamespaceNamesUseCase
) {

    @GetMapping
    fun getAllNamespaceNames(): NamespaceNamesDto {
        return NamespaceNamesDto(getNamespaceNamesUseCase.exec())
    }
}