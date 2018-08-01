package com.procurement.point.repository

import com.procurement.point.model.entity.ReleaseTenderEntity
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.data.cassandra.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ReleaseTenderRepository : CassandraRepository<ReleaseTenderEntity, String> {

    @Query(value = "select * from notice_compiled_release where cp_id=?0")
    fun getAllCompiledByCpId(cpId: String): List<ReleaseTenderEntity>

    @Query(value = "select * from notice_compiled_release where cp_id=?0 and release_date>=?1 ALLOW FILTERING")
    fun getAllCompiledByCpIdAndOffset(cpId: String, offset: Date): List<ReleaseTenderEntity>

    @Query(value = "select * from notice_compiled_release where cp_id=?0 and oc_id=?1 ALLOW FILTERING")
    fun getCompiledByCpIdAndOcid(cpId: String, ocId: String): ReleaseTenderEntity?
}