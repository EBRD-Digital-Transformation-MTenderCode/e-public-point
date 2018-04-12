package com.procurement.point

import com.procurement.point.config.ApplicationConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication(scanBasePackageClasses = [ApplicationConfig::class])
@EnableEurekaClient
class PublicPointApplication

fun main(args: Array<String>) {
    runApplication<PublicPointApplication>(*args)
}
