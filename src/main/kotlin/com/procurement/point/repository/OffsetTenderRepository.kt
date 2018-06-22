package com.procurement.point.repository

import com.procurement.point.model.entity.OffsetEntity
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.data.cassandra.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OffsetTenderRepository : CassandraRepository<OffsetEntity, String> {

    @Query(value = "SELECT * FROM notice_offset WHERE release_date>?0 LIMIT ?1 ALLOW FILTERING")
    fun getAllByOffset(offset: Date, limit: Int): List<OffsetEntity>
}