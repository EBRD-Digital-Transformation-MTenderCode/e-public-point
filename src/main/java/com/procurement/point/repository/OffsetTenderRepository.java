package com.procurement.point.repository;

import com.procurement.point.model.entity.OffsetEntity;
import com.procurement.point.model.entity.ReleaseEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OffsetTenderRepository extends CassandraRepository<OffsetEntity, String> {

    @Query(value = "SELECT * FROM notice_offset WHERE release_date>=?0 LIMIT ?1 ALLOW FILTERING")
    Optional<List<OffsetEntity>> getAllByOffset(Date offset, Integer limit);
}