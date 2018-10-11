package com.procurement.point.model.entity

import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import java.util.*

data class OffsetBudgetEntity(

        @PrimaryKeyColumn(name = "cp_id", type = PrimaryKeyType.PARTITIONED)
        val cpId: String,

        @Column(value = "release_date")
        val date: Date
)
