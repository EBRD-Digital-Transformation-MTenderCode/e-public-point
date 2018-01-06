package com.procurement.point.model.dto.offset;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.procurement.point.databinding.LocalDateTimeDeserializer;
import com.procurement.point.databinding.LocalDateTimeSerializer;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ocid",
        "date"
})
public class CpidDto {

    @JsonProperty("ocid")
    private final String ocid;

    @JsonProperty("date")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime date;

    @JsonCreator
    public CpidDto(@JsonProperty("ocid") final String ocid,
                   @JsonProperty("date") final LocalDateTime date) {
        this.ocid = ocid;
        this.date = date;
    }
}