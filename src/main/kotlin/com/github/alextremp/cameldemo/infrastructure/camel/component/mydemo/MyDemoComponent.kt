package com.github.alextremp.cameldemo.infrastructure.camel.component.mydemo

import com.github.alextremp.cameldemo.domain.logger.LoggerDelegate
import org.apache.camel.support.DefaultComponent
import org.springframework.stereotype.Component

@Component
class MyDemoComponent: DefaultComponent() {

  private val logger by LoggerDelegate()

  override fun createEndpoint(uri: String, remaining: String, parameters: MutableMap<String, Any>): MyDemoEndpoint {
    logger.info {  "Creating endpoint: $uri|$remaining|$parameters" }
    return MyDemoEndpoint(uri, this)
  }
}