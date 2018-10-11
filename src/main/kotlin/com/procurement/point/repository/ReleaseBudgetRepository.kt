package com.procurement.point.repository

import com.procurement.point.model.entity.ReleaseBudgetEntity
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.data.cassandra.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ReleaseBudgetRepository : CassandraRepository<ReleaseBudgetEntity, String> {

    @Query(value = "select * from notice_budget_compiled_release where cp_id=?0")
    fun getAllCompiledByCpId(cpId: String): List<ReleaseBudgetEntity>

    @Query(value = "select * from notice_budget_compiled_release where cp_id=?0 and release_date>=?1 ALLOW FILTERING")
    fun getAllCompiledByCpIdAndOffset(cpId: String, offset: Date): List<ReleaseBudgetEntity>

    @Query(value = "select * from notice_budget_compiled_release where cp_id=?0 and oc_id=?1 LIMIT 1")
    fun getCompiledByCpIdAndOcid(cpId: String, ocId: String): ReleaseBudgetEntity?

    @Query(value = "select * from notice_budget_release where cp_id=?0 and oc_id=?1")
    fun getAllReleasesByCpIdAndOcId(cpId: String, ocId: String): List<ReleaseBudgetEntity>

    @Query(value = "select * from notice_budget_release where cp_id=?0 and oc_id=?1 and release_date>=?2 ALLOW FILTERING")
    fun getAllReleasesByCpIdAndOcIdAndOffset(cpId: String, ocId: String, offset: Date): List<ReleaseBudgetEntity>


}