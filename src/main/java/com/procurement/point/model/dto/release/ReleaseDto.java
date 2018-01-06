package com.procurement.point.model.dto.release;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ocid",
        "compiledRelease"
})
public class ReleaseDto {

    @JsonProperty("ocid")
    private final String ocid;

    @JsonProperty("compiledRelease")
    private final JsonNode compiledRelease;

    @JsonCreator
    public ReleaseDto(@JsonProperty("ocid") final String ocid,
                      @JsonProperty("compiledRelease") final JsonNode compiledRelease) {
        this.ocid = ocid;
        this.compiledRelease = compiledRelease;
    }
}