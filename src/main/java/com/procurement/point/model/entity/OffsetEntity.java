package com.procurement.point.model.entity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;

@Getter
@Setter
@Table("notice_offset")
public class OffsetEntity {

    @PrimaryKeyColumn(name = "cp_id", type = PrimaryKeyType.PARTITIONED)
    private String cpId;

    @Column(value = "release_date")
    private Date date;

    public LocalDateTime getDate() {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneOffset.UTC);
    }

    public void setDate(LocalDateTime releaseDate) {
        this.date = Date.from(releaseDate.toInstant(ZoneOffset.UTC));
    }

}
