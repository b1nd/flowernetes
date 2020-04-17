package ru.flowernetes.script.data.controller

import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.flowernetes.entity.script.SourceScript
import ru.flowernetes.pagination.api.domain.entity.Order
import ru.flowernetes.pagination.api.domain.entity.Page
import ru.flowernetes.pagination.api.domain.entity.PageRequest
import ru.flowernetes.pagination.api.domain.entity.Sort
import ru.flowernetes.script.api.domain.dto.FileDto
import ru.flowernetes.script.api.domain.dto.SourceScriptDto
import ru.flowernetes.script.api.domain.usecase.AddSourceScriptUseCase
import ru.flowernetes.script.api.domain.usecase.DeleteSourceScriptUseCase
import ru.flowernetes.script.api.domain.usecase.GetSourceScriptByIdUseCase
import ru.flowernetes.script.api.domain.usecase.GetSourceScriptsUseCase


@RestController
@RequestMapping("/scripts/source")
class SourceScriptController(
  private val addSourceScriptUseCase: AddSourceScriptUseCase,
  private val getSourceScriptsUseCase: GetSourceScriptsUseCase,
  private val getSourceScriptByIdUseCase: GetSourceScriptByIdUseCase,
  private val deleteSourceScriptUseCase: DeleteSourceScriptUseCase
) {

    @PutMapping
    fun addSourceScript(
      @RequestPart("file") file: MultipartFile,
      @RequestPart("sourceScriptDto") sourceScriptDto: SourceScriptDto
    ): SourceScript {
        return addSourceScriptUseCase.exec(
          sourceScriptDto,
          FileDto(sourceScriptDto.filename, file.contentType, file.inputStream)
        )
    }

    @GetMapping
    fun getSourceScriptsPage(
      @RequestParam page: Int,
      @RequestParam size: Int,
      @RequestParam(defaultValue = "DESCENDING") order: Order,
      @RequestParam(defaultValue = "uploadDate") orderBy: Array<String>
    ): Page<SourceScript> {
        return getSourceScriptsUseCase.exec(PageRequest(page, size, Sort(order, orderBy.toList())))
    }

    @GetMapping("/{id}")
    fun getSourceScript(@PathVariable id: String): ResponseEntity<InputStreamResource> {
        val fileDto = getSourceScriptByIdUseCase.exec(id)
        val resource = InputStreamResource(fileDto.inputStream)
        val contentType = fileDto.contentType ?: MediaType.APPLICATION_OCTET_STREAM_VALUE

        return ResponseEntity.ok()
          .contentType(MediaType.parseMediaType(contentType))
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"${fileDto.name}\"")
          .body(resource)
    }

    @DeleteMapping("/{id}")
    fun deleteSourceScript(@PathVariable id: String) {
        deleteSourceScriptUseCase.exec(id)
    }
}