package com.procurement.point.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix = "ocds")
data class OCDSProperties(

        var extensions: List<String>?,

        var path: String?,

        var version: String?,

        var license: String?,

        var publicationPolicy: String?,

        var publisherName: String?,

        var publisherScheme: String?,

        var publisherUid: String?,

        var publisherUri: String?,

        var defLimit: Int?,

        var maxLimit: Int?
)
