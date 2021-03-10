package com.procurement.point.exception

data class InternalException(override val message: String) : RuntimeException()
