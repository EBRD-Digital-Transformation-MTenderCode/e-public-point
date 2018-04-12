package com.procurement.point.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(CassandraConfig::class, ServiceConfig::class, WebConfig::class, JsonConfig::class)
class ApplicationConfig
