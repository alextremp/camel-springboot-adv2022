package com.github.alextremp.cameldemo.infrastructure.camel.typeconverter

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.alextremp.cameldemo.domain.sample.SampleItem
import org.apache.camel.Converter
import org.apache.camel.TypeConverters
import org.springframework.stereotype.Component

@Component
class SampleItemTypeConverter(
  private val objectMapper: ObjectMapper
) : TypeConverters {

  @Converter
  fun convertToSampleItem(value: String) =
    objectMapper.readValue(value, SampleItem::class.java)
}