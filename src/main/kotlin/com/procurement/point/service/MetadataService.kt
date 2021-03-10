package com.procurement.point.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.procurement.point.exception.InternalException
import com.procurement.point.model.dto.RecordPackageMetadataDto
import com.procurement.point.repository.RecordPackageMetadataRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service


@Service
class MetadataService(
    private val recordPackageMetadataRepository: RecordPackageMetadataRepository,
    private val mapper: ObjectMapper
) {

    companion object {
        const val VERSION = "version"
        const val PUBLISHER_NAME = "publisher_name"
        const val PUBLISHER_URI = "publisher_uri"
        const val LICENSE = "license"
        const val PUBLICATION_POLICY = "publicationPolicy"
        const val EXTENSIONS = "extensions"
    }


    @Cacheable(cacheNames = ["metadata-cache"])
    fun getMetadata(): RecordPackageMetadataDto {
        println("Without cache")
        val metadataEntities = recordPackageMetadataRepository.getMetadata()
            .associateBy { it.attribute }

        val version = metadataEntities[VERSION]?.value ?: missingMetadataAttribute(VERSION)
        val publisherName = metadataEntities[PUBLISHER_NAME]?.value ?: missingMetadataAttribute(PUBLISHER_NAME)
        val publisherUri = metadataEntities[PUBLISHER_URI]?.value ?: missingMetadataAttribute(PUBLISHER_URI)
        val license = metadataEntities[LICENSE]?.value ?: missingMetadataAttribute(LICENSE)
        val publicationPolicy = metadataEntities[PUBLICATION_POLICY]?.value ?: missingMetadataAttribute(PUBLICATION_POLICY)
        val extensions = metadataEntities[EXTENSIONS]?.value
            ?.let { mapper.readTree(it) }
            ?.map { it.asText() }
            ?: missingMetadataAttribute(EXTENSIONS)

        return RecordPackageMetadataDto(
            version = version,
            publisherName = publisherName,
            publisherUri = publisherUri,
            license = license,
            publicationPolicy = publicationPolicy,
            extensions = extensions
        )
    }

    private fun missingMetadataAttribute(attribute: String): Nothing =
        throw InternalException("Missing '$attribute' in DB table for record package metadata.")

}

fun qwe(): String {
    return ""
}

