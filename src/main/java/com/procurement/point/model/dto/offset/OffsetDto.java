package com.procurement.point.model.dto.offset;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.procurement.point.databinding.LocalDateTimeDeserializer;
import com.procurement.point.databinding.LocalDateTimeSerializer;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "data",
        "offset"
})
public class OffsetDto {

    @JsonProperty("data")
    private final List<CpidDto> data;

    @JsonProperty("offset")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime offset;

    @JsonCreator
    public OffsetDto(@JsonProperty("data") final List<CpidDto> data,
                     @JsonProperty("offset") final LocalDateTime offset) {
        this.data = data;
        this.offset = offset;
    }
}