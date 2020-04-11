package ru.flowernetes

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class App

fun main(args: Array<String>) {
    ObjectMapper().registerModule(KotlinModule())
    runApplication<App>(*args)
}
