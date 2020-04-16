package ru.flowernetes.workflow.data.mapper

import org.springframework.stereotype.Component
import ru.flowernetes.entity.workflow.Workflow
import ru.flowernetes.util.mapper.Mapper
import ru.flowernetes.workflow.api.domain.dto.WorkflowDto

@Component
class WorkflowDtoMapper : Mapper<WorkflowDto, Workflow> {
    override fun map(it: WorkflowDto): Workflow = Workflow(
      name = it.name,
      isPublic = it.isPublic,
      team = it.team
    )
}