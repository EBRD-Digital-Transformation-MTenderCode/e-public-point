package com.procurement.point.model.dto.offset

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.procurement.point.databinding.JsonOffsetSerializer
import java.time.LocalDateTime

data class OffsetDto(

        @JsonProperty("data")
        val data: List<CpidDto>,

        @JsonProperty("offset")
        @JsonSerialize(using = JsonOffsetSerializer::class)
        val offset: LocalDateTime?
)