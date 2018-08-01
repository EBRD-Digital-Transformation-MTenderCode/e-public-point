package com.procurement.point.repository

import com.procurement.point.model.entity.OffsetTenderEntity
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.data.cassandra.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OffsetTenderRepository : CassandraRepository<OffsetTenderEntity, String> {

    @Query(value = "SELECT * FROM notice_offset WHERE release_date>?0 LIMIT ?1 ALLOW FILTERING")
    fun getAllByOffset(offset: Date, limit: Int): List<OffsetTenderEntity>

    @Query(value = "SELECT * FROM notice_offset WHERE status IN ?0 and release_date>?1 LIMIT ?2 ALLOW FILTERING")
    fun getAllByOffsetByStatus(statuses: List<String>, offset: Date, limit: Int): List<OffsetTenderEntity>

}