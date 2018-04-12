package com.procurement.point.model.dto.record

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonNode

data class RecordDto(

        @JsonProperty("ocid")
        val ocid: String,

        @JsonProperty("compiledRelease")
        val compiledRelease: JsonNode
)