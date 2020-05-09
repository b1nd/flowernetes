package ru.flowernetes.entity.workflow

data class Node<T>(
  val value: T,
  val type: NodeType
)