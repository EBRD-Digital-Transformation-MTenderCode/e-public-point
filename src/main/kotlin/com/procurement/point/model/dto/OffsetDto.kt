package com.procurement.point.model.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.procurement.point.databinding.JsonDateSerializer
import com.procurement.point.databinding.JsonOffsetSerializer
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class OffsetDto(

        @JsonProperty("data")
        val data: List<DataDto>?,

        @JsonProperty("offset")
        @JsonSerialize(using = JsonOffsetSerializer::class)
        val offset: LocalDateTime?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DataDto(

        @JsonProperty("ocid")
        val ocid: String,

        @JsonProperty("date")
        @JsonSerialize(using = JsonDateSerializer::class)
        val date: LocalDateTime?
)