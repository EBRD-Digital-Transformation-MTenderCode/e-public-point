package com.procurement.point.model.entity

import org.springframework.data.cassandra.core.cql.Ordering
import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import java.util.*

data class ReleaseTenderEntity(

        @PrimaryKeyColumn(name = "cp_id", type = PrimaryKeyType.PARTITIONED)
        val cpId: String,

        @PrimaryKeyColumn(name = "oc_id", type = PrimaryKeyType.CLUSTERED)
        val ocId: String,

        @PrimaryKeyColumn(name = "release_date", type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
        val releaseDate: Date,

        @PrimaryKeyColumn(name = "release_id", type = PrimaryKeyType.CLUSTERED)
        val releaseId: String,

        @Column(value = "stage")
        val stage: String,

        @Column(value = "publish_date")
        val publishDate: Date,

        @Column(value = "json_data")
        val jsonData: String,

        @Column(value = "status")
        var status: String? = ""
)
