package com.procurement.point.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import org.springframework.context.annotation.Configuration
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import javax.annotation.PostConstruct


@Configuration
class JsonConfig(private val mapper: ObjectMapper) {

    @PostConstruct
    fun init() {
        JsonMapper.init(mapper)
        DateFormatter.init()
    }

    object JsonMapper {
        lateinit var mapper: ObjectMapper
        fun init(objectMapper: ObjectMapper) {
            objectMapper.configure(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS, true)
            objectMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true)
            objectMapper.nodeFactory = JsonNodeFactory.withExactBigDecimals(true)
            mapper = objectMapper
        }
    }

    object DateFormatter {
        lateinit var formatter: DateTimeFormatter
        lateinit var formatterMillis: DateTimeFormatter
        fun init() {
            formatter = DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .append(DateTimeFormatter.ISO_LOCAL_DATE)
                    .appendLiteral('T')
                    .appendValue(ChronoField.HOUR_OF_DAY, 2)
                    .appendLiteral(':')
                    .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
                    .optionalStart()
                    .appendLiteral(':')
                    .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
                    .appendLiteral('Z')
                    .toFormatter()
            formatterMillis = DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .append(DateTimeFormatter.ISO_LOCAL_DATE)
                    .appendLiteral('T')
                    .appendValue(ChronoField.HOUR_OF_DAY, 2)
                    .appendLiteral(':')
                    .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
                    .optionalStart()
                    .appendLiteral(':')
                    .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
                    .optionalStart()
                    .appendLiteral('.')
                    .appendValue(ChronoField.MILLI_OF_SECOND, 3)
                    .appendLiteral('Z')
                    .toFormatter()
        }
    }
}
