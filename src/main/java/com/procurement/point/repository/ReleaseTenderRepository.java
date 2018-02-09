package com.procurement.point.repository;

import com.procurement.point.model.entity.ReleaseEntity;
import java.time.LocalDateTime;
import java.util.Date;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReleaseTenderRepository extends CassandraRepository<ReleaseEntity, String> {

    @Query(value = "select * from notice_compiled_release where cp_id=?0")
    List<ReleaseEntity> getAllByCpId(String cpId);

    @Query(value = "select * from notice_compiled_release where cp_id=?0 and release_date>=?1 ALLOW FILTERING")
    List<ReleaseEntity> getAllByCpIdAndOffset(String cpId, Date offset);

    @Query(value = "select * from notice_release where cp_id=?0 and oc_id=?1")
    List<ReleaseEntity> getAllByCpIdAndOcId(String cpId, String ocId);

    @Query(value = "select * from notice_release where cp_id=?0 and oc_id=?1 and release_date>=?2 ALLOW FILTERING")
    List<ReleaseEntity> getAllByCpIdAndOcIdAndOffset(String cpId, String ocId,Date offset);


}