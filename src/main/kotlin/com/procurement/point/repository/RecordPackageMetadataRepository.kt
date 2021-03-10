package com.procurement.point.repository

import com.procurement.point.model.entity.MetadataEntity
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.data.cassandra.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface RecordPackageMetadataRepository : CassandraRepository<MetadataEntity, String> {

    companion object {
        const val TABLE = "record_package_metadata"

        /**
         * Temporal Partition surrogate key.
         * All records will be saved at one node
         */
        const val COLUMN_ID = "id"
        const val COLUMN_ID_DEFAULT_VALUE = "metadata"
    }

    @Query(value = "SELECT * FROM $TABLE WHERE $COLUMN_ID=:id")
    fun getMetadata(@Param("id") id: String = COLUMN_ID_DEFAULT_VALUE): List<MetadataEntity>

}