package ru.flowernetes.entity.workflow

import ru.flowernetes.entity.monitoring.TaskStatusInfo

data class Graph<N, ID>(
  val nodes: List<Node<N>>,
  val edges: List<Edge<ID>>,
  val statuses: List<TaskStatusInfo>
)