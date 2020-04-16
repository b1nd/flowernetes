package ru.flowernetes.workflow.data.dto

import ru.flowernetes.entity.workflow.Workflow

data class AllWorkflowsDto(
  val items: List<Workflow>
)