package com.procurement.point.databinding

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.procurement.point.config.JsonConfig
import java.io.IOException
import java.time.LocalDateTime

class JsonDateDeserializer : JsonDeserializer<LocalDateTime>() {

    @Throws(IOException::class, JsonProcessingException::class)
    override fun deserialize(jsonParser: JsonParser, deserializationContext: DeserializationContext): LocalDateTime {
        val dateTime = jsonParser.text
        return LocalDateTime.parse(dateTime, JsonConfig.DateFormatter.formatter)
    }
}
