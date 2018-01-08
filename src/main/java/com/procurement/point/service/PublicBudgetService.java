package com.procurement.point.service;

import com.procurement.point.model.dto.offset.OffsetDto;
import com.procurement.point.model.dto.record.RecordPackageDto;
import com.procurement.point.model.dto.release.ReleasePackageDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface PublicBudgetService {

    RecordPackageDto getRecordPackage(String cpid, LocalDateTime offset);

    ReleasePackageDto getReleasePackage(String cpid, String ocid, LocalDateTime offset);

    OffsetDto getByOffset(LocalDateTime offset, Integer limit);
}