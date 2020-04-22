package ru.flowernetes.workload.data.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.pagination.api.domain.entity.Order
import ru.flowernetes.pagination.api.domain.entity.Page
import ru.flowernetes.pagination.api.domain.entity.PageRequest
import ru.flowernetes.pagination.api.domain.entity.Sort
import ru.flowernetes.workload.api.domain.usecase.GetWorkloadsUseCase

@RestController
@RequestMapping("/workloads")
class WorkloadController(
  private val getWorkloadsUseCase: GetWorkloadsUseCase
) {

    @GetMapping
    fun getWorkloadsPage(
      @RequestParam page: Int,
      @RequestParam size: Int,
      @RequestParam(defaultValue = "DESCENDING") order: Order,
      @RequestParam(defaultValue = "workloadCreationTime") orderBy: Array<String>
    ): Page<Workload> {
        return getWorkloadsUseCase.exec(PageRequest(page, size, Sort(order, orderBy.toList())))
    }
}