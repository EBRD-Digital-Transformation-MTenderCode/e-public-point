package com.procurement.point.model.entity

import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import java.util.*

data class MetadataEntity(

        @PrimaryKeyColumn(name = "id", type = PrimaryKeyType.PARTITIONED)
        val id: String,

        @PrimaryKeyColumn(name = "attribute", type = PrimaryKeyType.CLUSTERED)
        val attribute: String,

        @Column(value = "value")
        val value: String
)
