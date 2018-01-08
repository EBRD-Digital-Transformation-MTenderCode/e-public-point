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
import com.procurement.point.repository.OffsetTenderRepository;
import com.procurement.point.repository.ReleaseTenderRepository;
import com.procurement.point.utils.DateUtil;
import com.procurement.point.utils.JsonUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PublicBudgetServiceImpl implements PublicTenderService {

    private final ReleaseTenderRepository releaseTenderRepository;

    private final OffsetTenderRepository offsetTenderRepository;

    private final DateUtil dateUtil;

    private final JsonUtil jsonUtil;

    private final OCDSProperties ocds;

    public PublicBudgetServiceImpl(final ReleaseTenderRepository releaseTenderRepository,
                                   final OffsetTenderRepository offsetTenderRepository,
                                   final DateUtil dateUtil,
                                   final JsonUtil jsonUtil,
                                   final OCDSProperties ocds) {
        this.releaseTenderRepository = releaseTenderRepository;
        this.offsetTenderRepository = offsetTenderRepository;
        this.dateUtil = dateUtil;
        this.jsonUtil = jsonUtil;
        this.ocds = ocds;
    }

    @Override
    public RecordPackageDto getRecordPackage(final String cpid, final LocalDateTime offset) {

        Optional<List<ReleaseEntity>> entities;
        if (offset != null) {
            Date date = dateUtil.localDateTimeToDate(offset);
            entities = releaseTenderRepository.getAllByCpIdAndOffset(cpid, date);
        } else {
            entities = releaseTenderRepository.getAllByCpId(cpid);
        }
        if (entities.isPresent()) {
            return getRecordPackageDto(entities.get(), cpid);
        } else {
            throw new GetDataException("Nothing found on request.");
        }
    }

    @Override
    public ReleasePackageDto getReleasePackage(String cpid, String ocid, LocalDateTime offset) {
        Optional<List<ReleaseEntity>> entities;
        if (offset != null) {
            Date date = dateUtil.localDateTimeToDate(offset);
            entities = releaseTenderRepository.getAllByCpIdAndOcIdAndOffset(cpid, ocid, date);
        } else {
            entities = releaseTenderRepository.getAllByCpIdAndOcId(cpid, ocid);
        }

        if (entities.isPresent()) {
            List<ReleaseEntity> releaseEntities = entities.get();
            if (!releaseEntities.isEmpty()) {
                return getReleasePackageDto(releaseEntities, cpid);
            } else {
                throw new GetDataException("Nothing found on request.");
            }
        } else {
            throw new GetDataException("Nothing found on request.");
        }
    }

    @Override
    public OffsetDto getByOffset(LocalDateTime offset, Integer limit) {
        Optional<List<OffsetEntity>> entities = offsetTenderRepository.getAllByOffset(dateUtil.localDateTimeToDate(offset),
                                                                                limit);
        if (entities.isPresent()) {
            List<OffsetEntity> offsetEntities = entities.get();
            if (!offsetEntities.isEmpty()) {
                return getOffsetDto(offsetEntities);
            } else {
                throw new GetDataException("Nothing found on request.");
            }
        } else {
            throw new GetDataException("Nothing found on request.");
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

        final Date offset = entities.stream()
                                    .max(Comparator.comparing(OffsetEntity::getDate))
                                    .get()
                                    .getDate();
        final List<CpidDto> cpids = entities.stream()
                                            .map(e -> new CpidDto(e.getCpId(), dateUtil.dateToLocalDateTime(e.getDate
                                                ())))
                                            .collect(Collectors.toList());
        return new OffsetDto(cpids, dateUtil.dateToLocalDateTime(offset));
    }
}
