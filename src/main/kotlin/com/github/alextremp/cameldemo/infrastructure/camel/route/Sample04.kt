package com.github.alextremp.cameldemo.infrastructure.camel.route

import com.github.alextremp.cameldemo.domain.sample.SampleItem
import com.github.alextremp.cameldemo.domain.sample.SampleItemAddRequest
import org.apache.camel.builder.RouteBuilder
import org.springframework.stereotype.Component

/**
 * Sample04 Using type converters and kamelets.
 * Use SEDA to process items in parallel.
 * Avoid manual marshalling/unmarshalling.
 */
@Component
class Sample04 : RouteBuilder() {

  override fun configure() {

    from("direct:sample04-inbox")
      .routeId("sample04-inbox")
      .log("Received|\${body}")
      .kamelet("kafka-bus?topic=priv.cameldemo.sample04.inbox")

    from("kafka:priv.cameldemo.sample04.inbox")
      .routeId("sample04-priv.cameldemo.sample04.inbox")
      .split().jsonpath("$.data[*]").parallelProcessing()
      .kamelet("kafka-bus?topic=priv.cameldemo.sample04.item")

    from("kafka:priv.cameldemo.sample04.item")
      .routeId("sample04-priv.cameldemo.sample04.item")
      .log("Received Request|\${body}")
      .to("seda:sample04-item-queue")

    from("seda:sample04-item-queue?concurrentConsumers=5")
      .routeId("sample04-item-queue")
      .log("Add Request|\${body}")
      .convertBodyTo(SampleItemAddRequest::class.java)
      .convertBodyTo(SampleItem::class.java)
      .log("Sending Item|\${body}")
      .kamelet("kafka-bus?topic=priv.cameldemo.sample04.outbox")

    from("kafka:priv.cameldemo.sample04.outbox")
      .routeId("sample04-priv.cameldemo.sample04.outbox")
      .log("Outbox|\${body}")
  }
}