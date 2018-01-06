package com.procurement.point.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@Table("notice_release")
public class ReleaseEntity {

    @PrimaryKeyColumn(name = "cp_id", type = PrimaryKeyType.PARTITIONED)
    private String cpId;

    @PrimaryKeyColumn(name = "oc_id", type = PrimaryKeyType.CLUSTERED)
    private String ocId;

    @PrimaryKeyColumn(name = "release_date", type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private LocalDateTime releaseDate;

    @PrimaryKeyColumn(name = "release_id", type = PrimaryKeyType.CLUSTERED)
    private String releaseId;

    @Column(value = "stage")
    private String stage;

    @Column(value = "json_data")
    private String jsonData;
}
