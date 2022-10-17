package com.github.alextremp.cameldemo.infrastructure.camel.route

import com.github.alextremp.cameldemo.domain.sample.SampleItem
import com.github.alextremp.cameldemo.domain.sample.SampleItemAddRequest
import com.github.alextremp.cameldemo.infrastructure.camel.jackson.JsonMarshaler
import com.github.alextremp.cameldemo.infrastructure.camel.jackson.SampleAddRequestUnmarshaler
import org.apache.camel.builder.RouteBuilder
import org.springframework.stereotype.Component

/**
 * Sample03 More complex routing with Kafka integration.
 */
@Component
class Sample03(
  private val jsonMarshaler: JsonMarshaler,
  private val sampleAddRequestUnmarshaler: SampleAddRequestUnmarshaler
) : RouteBuilder() {

  override fun configure() {

    from("direct:sample03-inbox")
      .routeId("sample03-inbox")
      .log("Received|\${body}")
      .to("kafka:priv.cameldemo.sample03.inbox")

    from("kafka:priv.cameldemo.sample03.inbox")
      .routeId("sample03-priv.cameldemo.sample03.inbox")
      .split().jsonpath("$.data[*]").parallelProcessing()
      .to("kafka:priv.cameldemo.sample03.item")

    from("kafka:priv.cameldemo.sample03.item")
      .routeId("sample03-priv.cameldemo.sample03.item")
      .process(sampleAddRequestUnmarshaler)
      .log("Received Request:|\${body}")
      .process {
        it.message.body = it.message.getBody(SampleItemAddRequest::class.java)
          .let { request -> SampleItem(name = request.name) }
      }
      .process(jsonMarshaler)
      .log("Sending Item:|\${body}")
      .to("kafka:priv.cameldemo.sample03.outbox")

    from("kafka:priv.cameldemo.sample03.outbox")
      .routeId("sample03-priv.cameldemo.sample03.outbox")
      .log("Outbox:|\${body}")
  }

}