package com.procurement.point.service

import com.procurement.point.config.OCDSProperties
import com.procurement.point.exception.GetDataException
import com.procurement.point.exception.ParamException
import com.procurement.point.model.dto.*
import com.procurement.point.model.entity.OffsetBudgetEntity
import com.procurement.point.model.entity.ReleaseBudgetEntity
import com.procurement.point.repository.OffsetBudgetRepository
import com.procurement.point.repository.RecordPackageMetadataRepository
import com.procurement.point.repository.ReleaseBudgetRepository
import com.procurement.point.utils.epoch
import com.procurement.point.utils.toDate
import com.procurement.point.utils.toJsonNode
import com.procurement.point.utils.toLocal
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@EnableConfigurationProperties(OCDSProperties::class)
class PublicBudgetService(
        private val releaseBudgetRepository: ReleaseBudgetRepository,
        private val offsetBudgetRepository: OffsetBudgetRepository,
        private val metadataService: MetadataService,
        private val ocds: OCDSProperties) {

    private val defLimit: Int = ocds.defLimit ?: 100
    private val maxLimit: Int = ocds.maxLimit ?: 300


    fun getByOffset(offset: LocalDateTime?, limitParam: Int?): OffsetDto {
        val offsetParam = offset ?: epoch()
        val entities = offsetBudgetRepository.getAllByOffset(offsetParam.toDate())
        return when (!entities.isEmpty()) {
            true -> getOffsetDto(entities, getLimit(limitParam))
            else -> getEmptyOffsetDto()
        }
    }

    fun getRecordPackage(cpid: String, offset: LocalDateTime?): RecordPackageDto {
        val entities: List<ReleaseBudgetEntity>
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

    fun getRecord(cpid: String, ocid: String, offset: LocalDateTime?): ReleasePackageDto {
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

    private fun getRecordPackageDto(entities: List<ReleaseBudgetEntity>, cpid: String): RecordPackageDto {
        val publishedDate = entities.minBy { it.publishDate }?.publishDate?.toLocal()
        val records = entities.asSequence().sortedBy { it.releaseDate }
                .map { RecordDto(it.cpId, it.ocId, it.jsonData.toJsonNode()) }.toList()
        val recordUrls = records.map { ocds.path + "budgets/" + it.cpid + "/" + it.ocid }
        val metadata = metadataService.getMetadata()
        return RecordPackageDto(
                uri = ocds.path + "budgets/" + cpid,
                version = metadata.version,
                extensions = metadata.extensions.toList(),
                publisher = PublisherDto(
                        name = metadata.publisherName,
                        uri = metadata.publisherUri),
                license = metadata.license,
                publicationPolicy = metadata.publicationPolicy,
                publishedDate = publishedDate,
                packages = recordUrls,
                records = records,
                actualReleases = null
        )
    }

    private fun getReleasePackageDto(entities: List<ReleaseBudgetEntity>, cpid: String, ocid: String): ReleasePackageDto {
        val publishedDate = entities.minBy { it.publishDate }?.publishDate?.toLocal()
        val releases = entities.asSequence().sortedBy { it.releaseDate }
                .map { it.jsonData.toJsonNode() }.toList()
        val metadata = metadataService.getMetadata()
        return ReleasePackageDto(
                uri = ocds.path + "budgets/" + cpid + "/" + ocid,
                version = metadata.version,
                extensions = metadata.extensions.toList(),
                publisher = PublisherDto(
                        name = metadata.publisherName,
                        uri = metadata.publisherUri),
                license = metadata.license,
                publicationPolicy = metadata.publicationPolicy,
                publishedDate = publishedDate,
                releases = releases)
    }

    private fun getOffsetDto(entities: List<OffsetBudgetEntity>, limit: Int): OffsetDto {
        val entitiesList = entities.asSequence().sortedBy { it.date }.take(limit).toList()
        val cpIds = entitiesList.asSequence().map { DataDto(it.cpId, it.date.toLocal()) }.toList()
        return OffsetDto(data = cpIds, offset = cpIds.last().date)
    }

    private fun getEmptyOffsetDto(): OffsetDto {
        return OffsetDto(data = null, offset = null)
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
                records = null,
                actualReleases = null)
    }
}
