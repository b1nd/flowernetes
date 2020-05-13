package ru.flowernetes.workload.data.mapper

import org.springframework.stereotype.Component
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.util.mapper.Mapper
import ru.flowernetes.workload.api.domain.model.WorkloadModel
import java.time.LocalDateTime

@Component
class WorkloadModelMapper : Mapper<WorkloadModel, Workload> {
    override fun map(it: WorkloadModel): Workload {
        return Workload(
          workloadCreationTime = LocalDateTime.now(),
          lastTransitionTime = System.currentTimeMillis(),
          task = it.task,
          taskStatus = it.taskStatus,
          baseImage = it.baseImage,
          memoryRequest = it.memoryRequest,
          memoryLimit = it.memoryLimit,
          cpuRequest = it.cpuRequest,
          cpuLimit = it.cpuLimit,
          sourceScriptId = it.sourceScriptId
        )
    }
}