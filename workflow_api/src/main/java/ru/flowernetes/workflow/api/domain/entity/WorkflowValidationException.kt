package ru.flowernetes.workflow.api.domain.entity

class WorkflowValidationException(message: String)
    : IllegalArgumentException("Workflow validation failed: $message")