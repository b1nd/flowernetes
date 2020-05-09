package ru.flowernetes.entity.workflow

data class Edge<ID>(
  val from: ID,
  val to: ID,
  val type: EdgeType
)