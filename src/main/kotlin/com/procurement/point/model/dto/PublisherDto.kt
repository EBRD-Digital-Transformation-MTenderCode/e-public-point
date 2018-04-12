package com.procurement.point.model.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class PublisherDto(

        @JsonProperty("name")
        private val name: String?,

        @JsonProperty("scheme")
        private val scheme: String?,

        @JsonProperty("uid")
        private val uid: String?,

        @JsonProperty("uri")
        private val uri: String?
)