package com.procurement.point.model.dto.offset

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.procurement.point.databinding.JsonDateSerializer
import java.time.LocalDateTime

data class CpidDto(

        @JsonProperty("ocid")
        val ocid: String,

        @JsonProperty("date")
        @JsonSerialize(using = JsonDateSerializer::class)
        val date: LocalDateTime?
)