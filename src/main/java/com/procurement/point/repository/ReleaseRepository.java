package com.procurement.point.repository;

import com.procurement.point.model.entity.ReleaseEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReleaseRepository extends CassandraRepository<ReleaseEntity, String> {

    @Query(value = "select * from notice_compiled_release where cp_id=?0")
    Optional<List<ReleaseEntity>> getAllByCpId(String cpId);

    @Query(value = "select * from notice_release where cp_id=?0 and oc_id=?1")
    Optional<List<ReleaseEntity>> getAllByCpIdAndOcId(String cpId, String ocId);
}