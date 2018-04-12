package com.procurement.point.exception

data class GetDataException(override val message: String) : RuntimeException()
