package com.procurement.point.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "ocds")
data class OCDSProperties(

        var path: String?,

        var publisherScheme: String?,

        var publisherUid: String?,

        var defLimit: Int?,

        var maxLimit: Int?
)
