package com.procurement.point.model.dto.record

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonNode

data class RecordDto(

        @JsonIgnore
        val cpid: String,

        val ocid: String,

        val compiledRelease: JsonNode
)