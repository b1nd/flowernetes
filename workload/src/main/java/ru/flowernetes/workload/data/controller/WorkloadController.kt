package ru.flowernetes.workload.data.controller

import org.springframework.core.io.InputStreamResource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.pagination.api.domain.entity.Direction
import ru.flowernetes.pagination.api.domain.entity.Page
import ru.flowernetes.pagination.api.domain.entity.PageRequest
import ru.flowernetes.util.extensions.zip
import ru.flowernetes.util.file.toResponseEntity
import ru.flowernetes.workload.api.domain.usecase.GetWorkloadLogFileDtoByWorkloadIdUseCase
import ru.flowernetes.workload.api.domain.usecase.GetWorkloadOutputFileDtoByWorkloadIdUseCase
import ru.flowernetes.workload.api.domain.usecase.GetWorkloadsUseCase

@RestController
@RequestMapping("/workloads")
class WorkloadController(
  private val getWorkloadsUseCase: GetWorkloadsUseCase,
  private val getWorkloadLogFileDtoByWorkloadIdUseCase: GetWorkloadLogFileDtoByWorkloadIdUseCase,
  private val getWorkloadOutputFileDtoByWorkloadIdUseCase: GetWorkloadOutputFileDtoByWorkloadIdUseCase
) {

    @GetMapping
    fun getWorkloadsPage(
      @RequestParam page: Int,
      @RequestParam size: Int,
      @RequestParam(defaultValue = "workloadCreationTime") property: Array<String>,
      @RequestParam(defaultValue = "DESCENDING") direction: Array<Direction>
    ): Page<Workload> {
        return getWorkloadsUseCase.exec(PageRequest(page, size, zip(property, direction)))
    }

    @GetMapping("/{id}/log")
    fun getWorkloadLogFile(@PathVariable id: Long): ResponseEntity<InputStreamResource> {
        return getWorkloadLogFileDtoByWorkloadIdUseCase.exec(id).toResponseEntity()
    }

    @GetMapping("/{id}/output")
    fun getWorkloadOutputFile(@PathVariable id: Long): ResponseEntity<InputStreamResource> {
        return getWorkloadOutputFileDtoByWorkloadIdUseCase.exec(id).toResponseEntity()
    }
}