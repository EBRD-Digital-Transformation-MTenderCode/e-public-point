package com.procurement.point.service

import com.procurement.point.config.OCDSProperties
import com.procurement.point.exception.GetDataException
import com.procurement.point.model.dto.PublisherDto
import com.procurement.point.model.dto.offset.CpidDto
import com.procurement.point.model.dto.offset.OffsetDto
import com.procurement.point.model.dto.record.RecordDto
import com.procurement.point.model.dto.record.RecordPackageDto
import com.procurement.point.model.dto.release.ReleasePackageDto
import com.procurement.point.model.entity.OffsetEntity
import com.procurement.point.model.entity.ReleaseEntity
import com.procurement.point.repository.OffsetBudgetRepository
import com.procurement.point.repository.ReleaseBudgetRepository
import com.procurement.point.utils.toDate
import com.procurement.point.utils.toJsonNode
import com.procurement.point.utils.toLocal
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import java.time.LocalDateTime

interface PublicBudgetService {

    fun getRecordPackage(cpid: String, offset: LocalDateTime?): RecordPackageDto

    fun getReleasePackage(cpid: String, ocid: String, offset: LocalDateTime?): ReleasePackageDto

    fun getByOffset(offset: LocalDateTime, limit: Int): OffsetDto
}

@Service
@EnableConfigurationProperties(OCDSProperties::class)
class PublicBudgetServiceImpl(
        private val releaseBudgetRepository: ReleaseBudgetRepository,
        private val offsetBudgetRepository: OffsetBudgetRepository,
        private val ocds: OCDSProperties) : PublicBudgetService {

    override fun getRecordPackage(cpid: String, offset: LocalDateTime?): RecordPackageDto {
        val entities = when (offset) {
            null -> releaseBudgetRepository.getAllByCpId(cpid)
            else -> releaseBudgetRepository.getAllByCpIdAndOffset(cpid, offset.toDate())
        }
        when (!entities.isEmpty()) {
            true -> return getRecordPackageDto(entities, cpid)
            else -> throw GetDataException("No records found.")
        }
    }

    override fun getReleasePackage(cpid: String, ocid: String, offset: LocalDateTime?): ReleasePackageDto {
        val entities = when (offset) {
            null -> releaseBudgetRepository.getAllByCpIdAndOcId(cpid, ocid)
            else -> releaseBudgetRepository.getAllByCpIdAndOcIdAndOffset(cpid, ocid, offset.toDate())
        }
        when (!entities.isEmpty()) {
            true -> return getReleasePackageDto(entities, cpid)
            else -> throw GetDataException("No releases found.")
        }
    }

    override fun getByOffset(offset: LocalDateTime, limit: Int): OffsetDto {
        val entities = offsetBudgetRepository.getAllByOffset(offset.toDate(), limit)
        return when (!entities.isEmpty()) {
            true -> getOffsetDto(entities)
            else -> getEmptyOffsetDto(offset)
        }
    }

    private fun getRecordPackageDto(entities: List<ReleaseEntity>, cpid: String): RecordPackageDto {
        val publishedDate = entities.maxBy { it.releaseDate }?.releaseDate?.toLocal()
        val records = entities.asSequence().sortedByDescending { it.releaseDate }
                .map { RecordDto(it.ocId, it.jsonData.toJsonNode()) }.toList()
        val recordUrls = records.map { ocds.path + "budget/" + it.ocid }
        return RecordPackageDto(
                uri = ocds.path + cpid,
                version = ocds.version,
                extensions = ocds.extensions?.toList(),
                publisher = PublisherDto(
                        name = ocds.publisherName,
                        scheme = ocds.publisherScheme,
                        uid = ocds.publisherUid,
                        uri = ocds.publisherUri),
                license = ocds.license,
                publicationPolicy = ocds.publicationPolicy,
                publishedDate = publishedDate,
                packages = recordUrls,
                records = records)
    }

    private fun getReleasePackageDto(entities: List<ReleaseEntity>, cpid: String): ReleasePackageDto {
        val publishedDate = entities.maxBy { it.releaseDate }?.releaseDate?.toLocal()
        val releases = entities.asSequence().sortedByDescending { it.releaseDate }
                .map { it.jsonData.toJsonNode() }.toList()
        return ReleasePackageDto(
                uri = ocds.path + cpid,
                version = ocds.version,
                extensions = ocds.extensions?.toList(),
                publisher = PublisherDto(
                        name = ocds.publisherName,
                        scheme = ocds.publisherScheme,
                        uid = ocds.publisherUid,
                        uri = ocds.publisherUri),
                license = ocds.license,
                publicationPolicy = ocds.publicationPolicy,
                publishedDate = publishedDate,
                releases = releases)
    }

    private fun getOffsetDto(entities: List<OffsetEntity>): OffsetDto {
        val offset = entities.maxBy { it.date }?.date?.toLocal()
        val cpIds = entities.asSequence().sortedByDescending { it.date }
                .map { CpidDto(it.cpId, it.date.toLocal()) }.toList()
        return OffsetDto(data = cpIds, offset = offset)
    }

    private fun getEmptyOffsetDto(offset: LocalDateTime): OffsetDto {
        return OffsetDto(data = ArrayList(), offset = offset)
    }
}
