package ru.flowernetes.orchestration.api.domain.parser

interface MemoryQuantityParser {
    fun parse(memoryQuantity: String): Long
}