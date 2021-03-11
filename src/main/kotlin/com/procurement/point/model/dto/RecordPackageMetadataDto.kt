package com.procurement.point.model.dto

import java.io.Serializable

data class RecordPackageMetadataDto(

    val extensions: List<String>,

    val version: String,

    val license: String,

    val publicationPolicy: String,

    val publisherName: String,

    val publisherUri: String

): Serializable // for caching support