package com.procurement.point.model.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.procurement.point.databinding.JsonDateSerializer
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class RecordPackageDto(

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

        @JsonProperty("packages")
        val packages: List<String>?,

        @JsonProperty("records")
        val records: List<RecordDto>?,

        @JsonProperty("actualReleases")
        val actualReleases: List<ActualReleaseDto>?
)

data class RecordDto(

        @JsonIgnore
        val cpid: String,

        val ocid: String,

        val compiledRelease: JsonNode
)

data class ActualReleaseDto(

        val ocid: String,

        val uri: String
)