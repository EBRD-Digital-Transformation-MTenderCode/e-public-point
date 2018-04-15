package com.procurement.point.model.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class PublisherDto(

        @JsonProperty("name")
        val name: String?,

        @JsonProperty("scheme")
        val scheme: String?,

        @JsonProperty("uid")
        val uid: String?,

        @JsonProperty("uri")
        val uri: String?
)