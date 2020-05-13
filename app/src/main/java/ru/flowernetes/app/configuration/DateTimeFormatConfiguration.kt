package ru.flowernetes.app.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.time.format.DateTimeFormatter


@Configuration
open class DateTimeFormatConfiguration : WebMvcConfigurer {
    override fun addFormatters(registry: FormatterRegistry) {
        val registrar = DateTimeFormatterRegistrar()
        registrar.setDateFormatter(DateTimeFormatter.ISO_LOCAL_DATE)
        registrar.setDateTimeFormatter(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        registrar.registerFormatters(registry)
    }
}