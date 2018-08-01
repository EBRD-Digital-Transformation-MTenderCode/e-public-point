package com.procurement.point.model.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.procurement.point.databinding.JsonDateSerializer
import com.procurement.point.model.dto.PublisherDto
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ReleasePackageDto(

        @JsonProperty("uri")
        val uri: String?,

        @JsonProperty("version")
        val version: String?,

        @JsonProperty("extensions")
        val extensions: List<String>?,

        @JsonProperty("publisher")
        val publisher: PublisherDto?,

        @JsonProperty("license")
        val license: String?,

        @JsonProperty("publicationPolicy")
        val publicationPolicy: String?,

        @JsonProperty("publishedDate")
        @JsonSerialize(using = JsonDateSerializer::class)
        val publishedDate: LocalDateTime?,

        @JsonProperty("releases")
        val releases: List<JsonNode>?
)