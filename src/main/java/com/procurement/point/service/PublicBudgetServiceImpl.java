package com.procurement.point.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.procurement.point.config.properties.OCDSProperties;
import com.procurement.point.exception.GetDataException;
import com.procurement.point.model.dto.PublisherDto;
import com.procurement.point.model.dto.offset.CpidDto;
import com.procurement.point.model.dto.offset.OffsetDto;
import com.procurement.point.model.dto.record.RecordDto;
import com.procurement.point.model.dto.record.RecordPackageDto;
import com.procurement.point.model.dto.release.ReleasePackageDto;
import com.procurement.point.model.entity.OffsetEntity;
import com.procurement.point.model.entity.ReleaseEntity;
import com.procurement.point.repository.OffsetBudgetRepository;
import com.procurement.point.repository.ReleaseBudgetRepository;
import com.procurement.point.utils.DateUtil;
import com.procurement.point.utils.JsonUtil;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PublicBudgetServiceImpl implements PublicBudgetService {

    private final ReleaseBudgetRepository releaseBudgetRepository;

    private final OffsetBudgetRepository offsetBudgetRepository;

    private final DateUtil dateUtil;

    private final JsonUtil jsonUtil;

    private final OCDSProperties ocds;

    public PublicBudgetServiceImpl(final ReleaseBudgetRepository releaseBudgetRepository,
                                   final OffsetBudgetRepository offsetBudgetRepository,
                                   final DateUtil dateUtil,
                                   final JsonUtil jsonUtil,
                                   final OCDSProperties ocds) {
        this.releaseBudgetRepository = releaseBudgetRepository;
        this.offsetBudgetRepository = offsetBudgetRepository;
        this.dateUtil = dateUtil;
        this.jsonUtil = jsonUtil;
        this.ocds = ocds;
    }

    @Override
    public RecordPackageDto getRecordPackage(final String cpid, final LocalDateTime offset) {
        List<ReleaseEntity> entities;
        if (offset != null) {
            Date date = dateUtil.localDateTimeToDate(offset);
            entities = releaseBudgetRepository.getAllByCpIdAndOffset(cpid, date);
        } else {
            entities = releaseBudgetRepository.getAllByCpId(cpid);
        }
        if (!entities.isEmpty()) {
            return getRecordPackageDto(entities, cpid);
        } else {
            throw new GetDataException("No records found.");
        }
    }

    @Override
    public ReleasePackageDto getReleasePackage(String cpid, String ocid, LocalDateTime offset) {
        List<ReleaseEntity> entities;
        if (offset != null) {
            Date date = dateUtil.localDateTimeToDate(offset);
            entities = releaseBudgetRepository.getAllByCpIdAndOcIdAndOffset(cpid, ocid, date);
        } else {
            entities = releaseBudgetRepository.getAllByCpIdAndOcId(cpid, ocid);
        }
        if (!entities.isEmpty()) {
                return getReleasePackageDto(entities, cpid);
        } else {
            throw new GetDataException("No releases found.");
        }
    }

    @Override
    public OffsetDto getByOffset(LocalDateTime offset, Integer limit) {
        List<OffsetEntity> entities = offsetBudgetRepository
                .getAllByOffset(dateUtil.localDateTimeToDate(offset), limit);
        if (!entities.isEmpty()) {
                return getOffsetDto(entities);
        } else {
            throw new GetDataException("No data found.");
        }
    }

    private RecordPackageDto getRecordPackageDto(final List<ReleaseEntity> entities, final String cpid) {
        final LocalDateTime publishedDate = entities.stream()
                .max(Comparator.comparing(ReleaseEntity::getReleaseDate))
                .get()
                .getReleaseDate();
        final List<RecordDto> records = entities.stream()
                .map(e -> new RecordDto(e.getOcId(), jsonUtil.toJsonNode(e.getJsonData())))
                .collect(Collectors.toList());
        final List<String> recordUrls = records.stream()
                .map(r -> ocds.getPath() + r.getOcid())
                .collect(Collectors.toList());
        return new RecordPackageDto(
                ocds.getPath() + cpid,
                ocds.getVersion(),
                Arrays.asList(ocds.getExtensions()),
                new PublisherDto(ocds.getPublisherName(), ocds.getPublisherScheme(), ocds.getPublisherUid(), ocds
                        .getPublisherUri()),
                ocds.getLicense(),
                ocds.getPublicationPolicy(),
                publishedDate,
                recordUrls,
                records);
    }

    private ReleasePackageDto getReleasePackageDto(final List<ReleaseEntity> entities, final String cpid) {
        final LocalDateTime publishedDate = entities.stream()
                .max(Comparator.comparing(ReleaseEntity::getReleaseDate))
                .get()
                .getReleaseDate();
        final List<JsonNode> releases = entities.stream()
                .map(e -> jsonUtil.toJsonNode(e.getJsonData()))
                .collect(Collectors.toList());
        return new ReleasePackageDto(
                ocds.getPath() + cpid,
                ocds.getVersion(),
                Arrays.asList(ocds.getExtensions()),
                new PublisherDto(ocds.getPublisherName(), ocds.getPublisherScheme(), ocds.getPublisherUid(), ocds
                        .getPublisherUri()),
                ocds.getLicense(),
                ocds.getPublicationPolicy(),
                publishedDate,
                releases);
    }

    private OffsetDto getOffsetDto(final List<OffsetEntity> entities) {
        final LocalDateTime offset = entities.stream()
                .max(Comparator.comparing(OffsetEntity::getDate))
                .get()
                .getDate();
        final List<CpidDto> cpids = entities.stream()
                .map(e -> new CpidDto(e.getCpId(), e.getDate()))
                .collect(Collectors.toList());
        return new OffsetDto(cpids, offset);
    }
}
