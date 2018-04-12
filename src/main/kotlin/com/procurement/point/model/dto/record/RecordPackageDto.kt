package com.procurement.point.model.dto.record

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.procurement.point.databinding.JsonDateSerializer
import com.procurement.point.model.dto.PublisherDto
import java.time.LocalDateTime

data class RecordPackageDto(

        @param:JsonProperty("uri")
        val uri: String?,

        @param:JsonProperty("version")
        val version: String?,

        @param:JsonProperty("extensions")
        val extensions: List<String>?,

        @param:JsonProperty("publisher")
        val publisher: PublisherDto?,

        @param:JsonProperty("license")
        val license: String?,

        @param:JsonProperty("publicationPolicy")
        val publicationPolicy: String?,

        @param:JsonProperty("publishedDate")
        @field:JsonSerialize(using = JsonDateSerializer::class)
        val publishedDate: LocalDateTime?,

        @param:JsonProperty("packages")
        val packages: List<String>?,

        @param:JsonProperty("records")
        val records: List<RecordDto>?
)