package com.procurement.point.model.dto.offset

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.procurement.point.databinding.JsonDateSerializer
import java.time.LocalDateTime

data class OffsetDto(

        @JsonProperty("data")
        val data: List<CpidDto>,

        @JsonProperty("offset")
        @JsonSerialize(using = JsonDateSerializer::class)
        val offset: LocalDateTime?
)