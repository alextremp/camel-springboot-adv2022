package com.github.alextremp.cameldemo.infrastructure.camel.component.mydemo

import com.github.alextremp.cameldemo.domain.logger.LoggerDelegate
import org.apache.camel.Exchange
import org.apache.camel.support.DefaultProducer

class MyDemoProducer(private val myDemoEndpoint: MyDemoEndpoint) : DefaultProducer(myDemoEndpoint) {

  private val logger by LoggerDelegate()

  override fun process(exchange: Exchange) {
    logger.info { "Processing exchange: ${exchange.message.body}|${exchange.message.headers}" }
    val consumer = myDemoEndpoint.findConsumer()
      ?: throw IllegalStateException("No consumer found ${myDemoEndpoint.endpointKey}")
    consumer.processor.process(exchange)
  }
}
