package com.procurement.point.model.dto.record;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.procurement.point.databinding.LocalDateTimeDeserializer;
import com.procurement.point.databinding.LocalDateTimeSerializer;
import com.procurement.point.model.dto.PublisherDto;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "uri",
        "version",
        "extensions",
        "publisher",
        "license",
        "publicationPolicy",
        "publishedDate",
        "packages",
        "records"
})
public class RecordPackageDto {

    @JsonProperty("uri")
    private final String uri;

    @JsonProperty("version")
    private final String version;

    @JsonProperty("extensions")
    private final List<String> extensions;

    @JsonProperty("publisher")
    private final PublisherDto publisher;

    @JsonProperty("license")
    private final String license;

    @JsonProperty("publicationPolicy")
    private final String publicationPolicy;

    @JsonProperty("publishedDate")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime publishedDate;

    @JsonProperty("packages")
    private final List<String> packages;

    @JsonProperty("records")
    private final List<RecordDto> records;

    @JsonCreator
    public RecordPackageDto(@JsonProperty("uri") final String uri,
                            @JsonProperty("version") final String version,
                            @JsonProperty("extensions") final List<String> extensions,
                            @JsonProperty("publisher") final PublisherDto publisher,
                            @JsonProperty("license") final String license,
                            @JsonProperty("publicationPolicy") final String publicationPolicy,
                            @JsonProperty("publishedDate") final LocalDateTime publishedDate,
                            @JsonProperty("packages") final List<String> packages,
                            @JsonProperty("records") final List<RecordDto> records) {
        this.uri = uri;
        this.version = version;
        this.extensions = extensions;
        this.publisher = publisher;
        this.license = license;
        this.publicationPolicy = publicationPolicy;
        this.publishedDate = publishedDate;
        this.packages = packages;
        this.records = records;
    }

}