package com.github.alextremp.cameldemo.infrastructure.camel.component.mydemo

import org.apache.camel.Processor
import org.apache.camel.support.DefaultEndpoint

class MyDemoEndpoint(uri: String, myDemoComponent: MyDemoComponent): DefaultEndpoint(uri, myDemoComponent) {

  private val registeredConsumers = mutableMapOf<String, MyDemoConsumer>()

  override fun createProducer() = MyDemoProducer(this)

  override fun createConsumer(processor: Processor): MyDemoConsumer {
    val myDemoConsumer = MyDemoConsumer(this, processor)
    registeredConsumers[endpointKey] = myDemoConsumer
    return myDemoConsumer
  }

  override fun isSingleton() = true

  fun findConsumer() = registeredConsumers[endpointKey]
}

