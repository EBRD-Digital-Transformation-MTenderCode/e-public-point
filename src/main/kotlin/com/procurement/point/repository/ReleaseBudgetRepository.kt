package com.procurement.point.repository

import com.procurement.point.model.entity.ReleaseEntity
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.data.cassandra.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ReleaseBudgetRepository : CassandraRepository<ReleaseEntity, String> {

    @Query(value = "select * from notice_budget_compiled_release where cp_id=?0")
    fun getAllByCpId(cpId: String): List<ReleaseEntity>

    @Query(value = "select * from notice_budget_compiled_release where cp_id=?0 and release_date>=?1 ALLOW FILTERING")
    fun getAllByCpIdAndOffset(cpId: String, offset: Date): List<ReleaseEntity>

    @Query(value = "select * from notice_budget_release where cp_id=?0 and oc_id=?1")
    fun getAllByCpIdAndOcId(cpId: String, ocId: String): List<ReleaseEntity>

    @Query(value = "select * from notice_budget_release where cp_id=?0 and oc_id=?1 and release_date>=?2 ALLOW FILTERING")
    fun getAllByCpIdAndOcIdAndOffset(cpId: String, ocId: String, offset: Date): List<ReleaseEntity>


}