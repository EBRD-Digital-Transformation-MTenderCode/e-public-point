package com.procurement.point.model.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "data"
})
public class ResponseDto {

    @JsonProperty("data")
    @JsonPropertyDescription("Response data")
    private String data;

    @JsonCreator
    public ResponseDto(@JsonProperty("data") final String data) {
        this.data = data;
    }
}
