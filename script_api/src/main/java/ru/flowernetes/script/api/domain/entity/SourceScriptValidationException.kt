package ru.flowernetes.script.api.domain.entity

class SourceScriptValidationException(message: String)
    : IllegalArgumentException("Source script validation failed: $message")