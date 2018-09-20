package com.procurement.point

import com.procurement.point.config.ApplicationConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackageClasses = [ApplicationConfig::class])
class PublicPointApplication

fun main(args: Array<String>) {
    runApplication<PublicPointApplication>(*args)
}
