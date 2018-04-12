package com.procurement.point.utils

import com.fasterxml.jackson.databind.JsonNode
import com.procurement.point.config.JsonConfig

fun String.toJsonNode(): JsonNode = JsonConfig.JsonMapper.mapper.readTree(this)


