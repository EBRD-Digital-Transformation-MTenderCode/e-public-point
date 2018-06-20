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
import com.procurement.point.repository.OffsetTenderRepository
import com.procurement.point.repository.ReleaseTenderRepository
import com.procurement.point.utils.toDate
import com.procurement.point.utils.toJsonNode
import com.procurement.point.utils.toLocal
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import java.time.LocalDateTime

interface PublicTenderService {

    fun getRecordPackage(cpid: String, offset: LocalDateTime?): RecordPackageDto

    fun getReleasePackage(cpid: String, ocid: String, offset: LocalDateTime?): ReleasePackageDto

    fun getByOffset(offset: LocalDateTime, limitParam: Int?): OffsetDto

    fun getRecord(cpid: String, ocid: String, offset: LocalDateTime?): ReleasePackageDto
}

@Service
@EnableConfigurationProperties(OCDSProperties::class)
class PublicTenderServiceImpl(
        private val releaseTenderRepository: ReleaseTenderRepository,
        private val offsetTenderRepository: OffsetTenderRepository,
        private val ocds: OCDSProperties) : PublicTenderService {

    private val defLimit: Int = ocds.defLimit ?: 100
    private val maxLimit: Int = ocds.maxLimit ?: 300

    override fun getRecordPackage(cpid: String, offset: LocalDateTime?): RecordPackageDto {
        val entities: List<ReleaseEntity>
        return if (offset == null) {
            entities = releaseTenderRepository.getAllCompiledByCpId(cpid)
            when (entities.isNotEmpty()) {
                true -> getRecordPackageDto(entities, cpid)
                else -> throw GetDataException("No releases found.")
            }
        } else {
            entities = releaseTenderRepository.getAllCompiledByCpIdAndOffset(cpid, offset.toDate())
            when (entities.isNotEmpty()) {
                true -> getRecordPackageDto(entities, cpid)
                else -> getEmptyRecordPackageDto()
            }
        }
    }

    override fun getReleasePackage(cpid: String, ocid: String, offset: LocalDateTime?): ReleasePackageDto {
        val entities: List<ReleaseEntity>
        return if (offset == null) {
            entities = releaseTenderRepository.getAllReleasesByCpIdAndOcId(cpid, ocid)
            when (entities.isNotEmpty()) {
                true -> getReleasePackageDto(entities, cpid)
                else -> throw GetDataException("No releases found.")
            }
        } else {
            entities = releaseTenderRepository.getAllReleasesByCpIdAndOcIdAndOffset(cpid, ocid, offset.toDate())
            when (entities.isNotEmpty()) {
                true -> getReleasePackageDto(entities, cpid)
                else -> getEmptyReleasePackageDto()
            }
        }
    }

    override fun getByOffset(offset: LocalDateTime, limitParam: Int?): OffsetDto {
        val entities = offsetTenderRepository.getAllByOffset(offset.toDate(), getLimit(limitParam))
        return when (!entities.isEmpty()) {
            true -> getOffsetDto(entities)
            else -> getEmptyOffsetDto(offset)
        }
    }

    override fun getRecord(cpid: String, ocid: String, offset: LocalDateTime?): ReleasePackageDto {
        val entity = releaseTenderRepository.getCompiledByCpIdAndOcid(cpid, ocid)
                ?: throw GetDataException("No releases found.")
        return if (offset != null) {
            if (entity.releaseDate >= offset.toDate()) {
                getReleasePackageDto(listOf(entity), cpid)
            } else {
                getEmptyReleasePackageDto()
            }
        } else {
            getReleasePackageDto(listOf(entity), cpid)
        }
    }

    private fun getLimit(limitParam: Int?): Int {
        return when (limitParam) {
            null -> defLimit
            else -> when {
                limitParam > maxLimit -> maxLimit
                else -> limitParam
            }
        }
    }

    private fun getRecordPackageDto(entities: List<ReleaseEntity>, cpid: String): RecordPackageDto {
        val publishedDate = entities.maxBy { it.releaseDate }?.releaseDate?.toLocal()
        val records = entities.asSequence().sortedByDescending { it.releaseDate }
                .map { RecordDto(it.ocId, it.jsonData.toJsonNode()) }.toList()
        val recordUrls = records.map { ocds.path + "tender/" + it.ocid }
        return RecordPackageDto(
                uri = ocds.path + "tender/" + cpid,
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
                uri = ocds.path + "tender/" + cpid,
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

    private fun getEmptyReleasePackageDto(): ReleasePackageDto {
        return ReleasePackageDto(
                uri = null,
                version = null,
                extensions = null,
                publisher = null,
                license = null,
                publicationPolicy = null,
                publishedDate = null,
                releases = null)
    }

    private fun getEmptyRecordPackageDto(): RecordPackageDto {
        return RecordPackageDto(
                uri = null,
                version = null,
                extensions = null,
                publisher = null,
                license = null,
                publicationPolicy = null,
                publishedDate = null,
                packages = null,
                records = null)
    }
}
