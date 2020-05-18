package ru.flowernetes.script.api.domain.entity

class UnsupportedScriptExtension(path: String)
    : UnsupportedOperationException("Path to run file has unsupported extension: $path")