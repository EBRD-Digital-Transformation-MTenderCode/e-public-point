package com.procurement.point.service

import com.procurement.point.config.OCDSProperties
import com.procurement.point.exception.GetDataException
import com.procurement.point.exception.ParamException
import com.procurement.point.model.dto.PublisherDto
import com.procurement.point.model.dto.offset.CpidDto
import com.procurement.point.model.dto.offset.OffsetDto
import com.procurement.point.model.dto.record.Record
import com.procurement.point.model.dto.record.RecordPackage
import com.procurement.point.model.dto.release.ReleasePackageDto
import com.procurement.point.model.entity.OffsetEntity
import com.procurement.point.model.entity.ReleaseEntity
import com.procurement.point.repository.OffsetBudgetRepository
import com.procurement.point.repository.ReleaseBudgetRepository
import com.procurement.point.utils.epoch
import com.procurement.point.utils.toDate
import com.procurement.point.utils.toJsonNode
import com.procurement.point.utils.toLocal
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import java.time.LocalDateTime

interface PublicBudgetService {

    fun getRecordPackage(cpid: String, offset: LocalDateTime?): RecordPackage

    fun getReleasePackage(cpid: String, ocid: String, offset: LocalDateTime?): ReleasePackageDto

    fun getByOffset(offset: LocalDateTime?, limitParam: Int?): OffsetDto

    fun getRecord(cpid: String, ocid: String, offset: LocalDateTime?): ReleasePackageDto
}

@Service
@EnableConfigurationProperties(OCDSProperties::class)
class PublicBudgetServiceImpl(
        private val releaseBudgetRepository: ReleaseBudgetRepository,
        private val offsetBudgetRepository: OffsetBudgetRepository,
        private val ocds: OCDSProperties) : PublicBudgetService {

    private val defLimit: Int = ocds.defLimit ?: 100
    private val maxLimit: Int = ocds.maxLimit ?: 300

    override fun getRecordPackage(cpid: String, offset: LocalDateTime?): RecordPackage {
        val entities: List<ReleaseEntity>
        return if (offset == null) {
            entities = releaseBudgetRepository.getAllCompiledByCpId(cpid)
            when (entities.isNotEmpty()) {
                true -> getRecordPackageDto(entities, cpid)
                else -> throw GetDataException("No releases found.")
            }
        } else {
            entities = releaseBudgetRepository.getAllCompiledByCpIdAndOffset(cpid, offset.toDate())
            when (entities.isNotEmpty()) {
                true -> getRecordPackageDto(entities, cpid)
                else -> getEmptyRecordPackageDto()
            }
        }
    }

    override fun getReleasePackage(cpid: String, ocid: String, offset: LocalDateTime?): ReleasePackageDto {
        val entities: List<ReleaseEntity>
        return if (offset == null) {
            entities = releaseBudgetRepository.getAllReleasesByCpIdAndOcId(cpid, ocid)
            when (entities.isNotEmpty()) {
                true -> getReleasePackageDto(entities, cpid, ocid)
                else -> throw GetDataException("No releases found.")
            }
        } else {
            entities = releaseBudgetRepository.getAllReleasesByCpIdAndOcIdAndOffset(cpid, ocid, offset.toDate())
            when (entities.isNotEmpty()) {
                true -> getReleasePackageDto(entities, cpid, ocid)
                else -> getEmptyReleasePackageDto()
            }
        }
    }

    override fun getByOffset(offset: LocalDateTime?, limitParam: Int?): OffsetDto {
        val offsetParam = offset ?: epoch()
        val entities = offsetBudgetRepository.getAllByOffset(offsetParam.toDate(), getLimit(limitParam))
        return when (!entities.isEmpty()) {
            true -> getOffsetDto(entities)
            else -> getEmptyOffsetDto(offsetParam)
        }
    }

    override fun getRecord(cpid: String, ocid: String, offset: LocalDateTime?): ReleasePackageDto {
        val entity = releaseBudgetRepository.getCompiledByCpIdAndOcid(cpid, ocid)
                ?: throw GetDataException("No releases found.")
        return if (offset != null) {
            if (entity.releaseDate >= offset.toDate()) {
                getReleasePackageDto(listOf(entity), cpid, ocid)
            } else {
                getEmptyReleasePackageDto()
            }
        } else {
            getReleasePackageDto(listOf(entity), cpid, ocid)
        }
    }

    private fun getLimit(limitParam: Int?): Int {
        return when (limitParam) {
            null -> defLimit
            else -> when {
                limitParam < 0 -> throw ParamException("Limit invalid.")
                limitParam > maxLimit -> maxLimit
                else -> limitParam
            }
        }
    }

    private fun getRecordPackageDto(entities: List<ReleaseEntity>, cpid: String): RecordPackage {
        val publishedDate = entities.minBy { it.releaseDate }?.releaseDate?.toLocal()
        val records = entities.asSequence().sortedBy { it.releaseDate }
                .map { Record(it.cpId, it.ocId, it.jsonData.toJsonNode()) }.toList()
        val recordUrls = records.map { ocds.path + "budgets/" + it.cpid + "/" + it.ocid }
        return RecordPackage(
                uri = ocds.path + "budgets/" + cpid,
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
                records = records,
                actualReleases = null
        )
    }

    private fun getReleasePackageDto(entities: List<ReleaseEntity>, cpid: String, ocid: String): ReleasePackageDto {
        val publishedDate = entities.minBy { it.releaseDate }?.releaseDate?.toLocal()
        val releases = entities.asSequence().sortedBy { it.releaseDate }
                .map { it.jsonData.toJsonNode() }.toList()
        return ReleasePackageDto(
                uri = ocds.path + "budgets/" + cpid + "/" + ocid,
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
        val cpIds = entities.asSequence().sortedBy { it.date }
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

    private fun getEmptyRecordPackageDto(): RecordPackage {
        return RecordPackage(
                uri = null,
                version = null,
                extensions = null,
                publisher = null,
                license = null,
                publicationPolicy = null,
                publishedDate = null,
                packages = null,
                records = null,
                actualReleases = null)
    }
}
