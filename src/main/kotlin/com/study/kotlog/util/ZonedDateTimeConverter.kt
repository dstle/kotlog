package com.study.kotlog.util

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.time.ZonedDateTime

@Converter(autoApply = false)
class ZonedDateTimeConverter : AttributeConverter<ZonedDateTime, String> {
    override fun convertToDatabaseColumn(attribute: ZonedDateTime?): String? = attribute?.toString()

    override fun convertToEntityAttribute(dbData: String?): ZonedDateTime? = dbData?.let { ZonedDateTime.parse(it) }
}
