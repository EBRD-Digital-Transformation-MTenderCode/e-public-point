package com.procurement.point.repository

import com.procurement.point.model.entity.OffsetTenderEntity
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.data.cassandra.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OffsetTenderRepository : CassandraRepository<OffsetTenderEntity, String> {

    @Query(value = "SELECT * FROM notice_offset WHERE release_date>?0 ALLOW FILTERING")
    fun getAllByOffset(offset: Date): List<OffsetTenderEntity>

    @Query(value = "SELECT * FROM notice_offset WHERE status = ?0 and release_date>?1 ALLOW FILTERING")
    fun getAllByOffsetAndStatus(status: String, offset: Date): List<OffsetTenderEntity>

    @Query(value = "SELECT stage FROM notice_offset WHERE cp_id = ?0")
    fun getStageByCpid(cpid: String): String?

}