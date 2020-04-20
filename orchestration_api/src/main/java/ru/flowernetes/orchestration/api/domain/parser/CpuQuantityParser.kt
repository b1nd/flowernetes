package ru.flowernetes.orchestration.api.domain.parser

interface CpuQuantityParser {
    fun parse(cpuQuantity: String): Double
}