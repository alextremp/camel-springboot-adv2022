package com.github.alextremp.cameldemo.infrastructure.camel.typeconverter

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.alextremp.cameldemo.domain.sample.SampleItem
import com.github.alextremp.cameldemo.domain.sample.SampleItemAddRequest
import org.apache.camel.Converter
import org.apache.camel.TypeConverters
import org.springframework.stereotype.Component

@Component
class SampleItemAddRequestTypeConverter(
  private val objectMapper: ObjectMapper
): TypeConverters {

  @Converter
  fun convertToSampleItemAddRequest(input: String): SampleItemAddRequest =
    objectMapper.readValue(input, SampleItemAddRequest::class.java)

  @Converter
  fun convertToSampleItem(input: SampleItemAddRequest): SampleItem =
    SampleItem(name = input.name)
}