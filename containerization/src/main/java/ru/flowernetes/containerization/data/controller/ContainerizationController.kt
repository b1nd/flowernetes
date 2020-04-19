package ru.flowernetes.containerization.data.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.flowernetes.containerization.api.domain.usecase.GetBaseImageNamesUseCase
import ru.flowernetes.containerization.data.dto.BaseImagesDto

@RestController
@RequestMapping("/containerization")
class ContainerizationController(
  private val getBaseImageNamesUseCase: GetBaseImageNamesUseCase
) {

    @GetMapping("/images")
    fun getBaseImages(): BaseImagesDto {
        return BaseImagesDto(getBaseImageNamesUseCase.exec())
    }
}