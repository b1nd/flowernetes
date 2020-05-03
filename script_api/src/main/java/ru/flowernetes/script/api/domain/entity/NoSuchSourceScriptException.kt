package ru.flowernetes.script.api.domain.entity

class NoSuchSourceScriptException(sourceScriptId: String)
    : NoSuchElementException("There is no source script with id $sourceScriptId")