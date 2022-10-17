package com.github.alextremp.cameldemo.infrastructure.camel.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.camel.Exchange
import org.apache.camel.Processor
import org.springframework.beans.factory.annotation.Autowired

abstract class AbstractPojoUnmarshaler<T>(
  private val type: Class<T>
) : Processor {

  @Autowired
  private lateinit var objectMapper: ObjectMapper

  override fun process(exchange: Exchange) {
    val json = exchange.message.getBody(String::class.java)
    val pojo = objectMapper.readValue(json, type)
    exchange.message.body = pojo
  }
}