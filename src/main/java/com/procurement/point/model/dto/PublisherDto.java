package com.procurement.point.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "scheme",
        "uid",
        "uri"
})
public class PublisherDto {

    @JsonProperty("name")
    private final  String name;

    @JsonProperty("scheme")
    private final  String scheme;

    @JsonProperty("uid")
    private final  String uid;

    @JsonProperty("uri")
    private final  String uri;

    @JsonCreator
    public PublisherDto(@JsonProperty("name") final String name,
                        @JsonProperty("scheme") final String scheme,
                        @JsonProperty("uid") final String uid,
                        @JsonProperty("uri") final String uri) {
        this.name = name;
        this.scheme = scheme;
        this.uid = uid;
        this.uri = uri;
    }


}