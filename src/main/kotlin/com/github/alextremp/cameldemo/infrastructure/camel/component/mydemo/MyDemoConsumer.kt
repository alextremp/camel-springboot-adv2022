package com.github.alextremp.cameldemo.infrastructure.camel.component.mydemo

import com.github.alextremp.cameldemo.domain.logger.LoggerDelegate
import org.apache.camel.Processor
import org.apache.camel.support.DefaultConsumer

class MyDemoConsumer(private val myDemoEndpoint: MyDemoEndpoint, processor: Processor) :
  DefaultConsumer(myDemoEndpoint, processor) {

  private val logger by LoggerDelegate()
  override fun doStart() {
    super.doStart()
    logger.info {"Starting consumer on ${myDemoEndpoint.endpointKey}"}
  }

  override fun doStop() {
    super.doStop()
    logger.info {"Stopping consumer on ${myDemoEndpoint.endpointKey}"}
  }
}
