package ru.flowernetes.orchestration.domain.parser

import io.fabric8.kubernetes.api.model.Quantity
import org.springframework.stereotype.Component
import ru.flowernetes.orchestration.api.domain.parser.MemoryQuantityParser

@Component
class MemoryQuantityParserImpl : MemoryQuantityParser {
    override fun parse(memoryQuantity: String): Long {
        return Quantity.getAmountInBytes(Quantity(memoryQuantity)).longValueExact()
    }
}