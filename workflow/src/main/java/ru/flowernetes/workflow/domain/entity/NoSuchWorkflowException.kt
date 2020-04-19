package ru.flowernetes.workflow.domain.entity

class NoSuchWorkflowException(id: Long) : NoSuchElementException("There is no workflow with id: $id")