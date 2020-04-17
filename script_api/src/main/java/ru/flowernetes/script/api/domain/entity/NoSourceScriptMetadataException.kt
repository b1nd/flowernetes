package ru.flowernetes.script.api.domain.entity

class NoSourceScriptMetadataException(id: String) : Throwable("No metadata for source script: $id")