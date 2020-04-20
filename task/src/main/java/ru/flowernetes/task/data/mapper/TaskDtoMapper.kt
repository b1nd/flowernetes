package ru.flowernetes.task.data.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import ru.flowernetes.entity.task.Task
import ru.flowernetes.orchestration.api.domain.parser.CpuQuantityParser
import ru.flowernetes.orchestration.api.domain.parser.MemoryQuantityParser
import ru.flowernetes.task.api.domain.dto.TaskDto
import ru.flowernetes.util.mapper.Mapper

@Component
class TaskDtoMapper(
  private val objectMapper: ObjectMapper,
  private val memoryQuantityParser: MemoryQuantityParser,
  private val cpuQuantityParser: CpuQuantityParser
) : Mapper<TaskDto, Task> {
    override fun map(it: TaskDto): Task {
        return Task(
          name = it.name,
          workflow = it.workflow,
          conditionJson = objectMapper.writeValueAsString(it.condition),
          baseImage = it.baseImage,
          cpuRequest = cpuQuantityParser.parse(it.cpuRequest),
          memoryRequest = memoryQuantityParser.parse(it.memoryRequest),
          cpuLimit = cpuQuantityParser.parse(it.cpuLimit),
          memoryLimit = memoryQuantityParser.parse(it.memoryLimit),
          sourceScriptId = it.sourceScriptId
        )
    }
}