package ru.flowernetes.script.data.controller

import org.springframework.core.io.InputStreamResource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.flowernetes.entity.file.FileDto
import ru.flowernetes.entity.script.SourceScript
import ru.flowernetes.pagination.api.domain.entity.Direction
import ru.flowernetes.pagination.api.domain.entity.Page
import ru.flowernetes.pagination.api.domain.entity.PageRequest
import ru.flowernetes.script.api.domain.dto.SourceScriptDto
import ru.flowernetes.script.api.domain.dto.SourceScriptFilter
import ru.flowernetes.script.api.domain.usecase.*
import ru.flowernetes.util.extensions.zip
import ru.flowernetes.util.file.toResponseEntity


@RestController
@RequestMapping("/scripts/source")
class SourceScriptController(
  private val addSourceScriptUseCase: AddSourceScriptUseCase,
  private val getSourceScriptsUseCase: GetSourceScriptsUseCase,
  private val getAllCallingTeamSourceScriptsUseCase: GetAllCallingTeamSourceScriptsUseCase,
  private val getSourceScriptFileDtoByIdUseCase: GetSourceScriptFileDtoByIdUseCase,
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

    @GetMapping("/session")
    fun getAllTeamSourceScripts(): List<SourceScript> {
        return getAllCallingTeamSourceScriptsUseCase.exec()
    }

    @GetMapping
    fun getSourceScriptsPage(
      @RequestParam page: Int,
      @RequestParam size: Int,
      @RequestParam(defaultValue = "uploadDate") property: Array<String>,
      @RequestParam(defaultValue = "DESCENDING") direction: Array<Direction>
    ): Page<SourceScript> {
        return getSourceScriptsUseCase.exec(PageRequest(page, size, zip(property, direction)))
    }

    @PostMapping
    fun getSourceScriptsPage(
      @RequestParam page: Int,
      @RequestParam size: Int,
      @RequestParam(defaultValue = "uploadDate") property: Array<String>,
      @RequestParam(defaultValue = "DESCENDING") direction: Array<Direction>,
      @RequestBody sourceScriptFilter: SourceScriptFilter
    ): Page<SourceScript> {
        return getSourceScriptsUseCase.exec(PageRequest(page, size, zip(property, direction)), sourceScriptFilter)
    }

    @GetMapping("/{id}")
    fun getSourceScript(@PathVariable id: String): SourceScript {
        return getSourceScriptByIdUseCase.exec(id)
    }

    @GetMapping("/{id}/file")
    fun getSourceScriptFile(@PathVariable id: String): ResponseEntity<InputStreamResource> {
        return getSourceScriptFileDtoByIdUseCase.exec(id).toResponseEntity()
    }

    @DeleteMapping("/{id}")
    fun deleteSourceScript(@PathVariable id: String) {
        deleteSourceScriptUseCase.exec(id)
    }
}