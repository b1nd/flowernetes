package ru.flowernetes.orchestration.domain.parser

import io.fabric8.kubernetes.api.model.Quantity
import org.springframework.stereotype.Component
import ru.flowernetes.orchestration.api.domain.parser.CpuQuantityParser

@Component
class CpuQuantityParserImpl : CpuQuantityParser {
    override fun parse(cpuQuantity: String): Double {
        return Quantity.getAmountInBytes(Quantity(cpuQuantity)).toDouble()
    }
}