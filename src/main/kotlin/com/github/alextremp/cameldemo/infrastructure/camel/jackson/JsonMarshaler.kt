package com.github.alextremp.cameldemo.infrastructure.camel.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.camel.Exchange
import org.apache.camel.Processor
import org.springframework.stereotype.Component

@Component
class JsonMarshaler(
  private val objectMapper: ObjectMapper
) : Processor {

  override fun process(exchange: Exchange) {
    val body = exchange.message.body
    exchange.message.body = when(body) {
      is String -> body
      else -> objectMapper.writeValueAsString(body)
    }
  }
}