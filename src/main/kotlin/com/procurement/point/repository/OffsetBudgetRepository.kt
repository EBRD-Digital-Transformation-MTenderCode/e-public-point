package com.procurement.point.repository

import com.procurement.point.model.entity.OffsetBudgetEntity
import com.procurement.point.model.entity.OffsetTenderEntity
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.data.cassandra.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OffsetBudgetRepository : CassandraRepository<OffsetBudgetEntity, String> {

    @Query(value = "SELECT * FROM notice_budget_offset WHERE release_date>?0 ALLOW FILTERING")
    fun getAllByOffset(offset: Date): List<OffsetBudgetEntity>

}