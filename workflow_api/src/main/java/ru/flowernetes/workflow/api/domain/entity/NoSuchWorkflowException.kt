package ru.flowernetes.workflow.api.domain.entity

class NoSuchWorkflowException(id: Long) : NoSuchElementException("There is no workflow with id: $id")