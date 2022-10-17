package com.github.alextremp.cameldemo.infrastructure.camel.kafka

import com.github.alextremp.cameldemo.infrastructure.camel.kafka.AdvKafkaHeaders.isAdvKafkaHeader
import org.apache.camel.Exchange
import org.apache.camel.spi.HeaderFilterStrategy
import org.springframework.stereotype.Component
import java.util.logging.Logger

@Component
class AdvKafkaHeadersFilter : HeaderFilterStrategy {

  override fun applyFilterToCamelHeaders(headerName: String?, headerValue: Any?, exchange: Exchange?): Boolean {
    return applyFilter(headerName)
  }

  override fun applyFilterToExternalHeaders(headerName: String?, headerValue: Any?, exchange: Exchange?): Boolean {
    return applyFilter(headerName)
  }

  private fun applyFilter(headerName: String?): Boolean {
    return headerName == null || !isAdvKafkaHeader(headerName)
  }
}