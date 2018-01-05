package com.procurement.point.repository;

import com.procurement.point.model.entity.PublicEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PublicRepository extends CassandraRepository<PublicEntity, String> {
    @Query(value = "select * from storage_files where file_id=?0 LIMIT 1")
    Optional<PublicEntity> getOneById(UUID fileId);

    @Query(value = "select * from storage_files where file_id=?0 and file_is_open=?1 LIMIT 1")
    Optional<PublicEntity> getOpenById(UUID fileId, Boolean isOpen);
}