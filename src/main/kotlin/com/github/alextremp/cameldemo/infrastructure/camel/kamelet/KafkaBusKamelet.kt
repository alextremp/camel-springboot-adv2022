package com.github.alextremp.cameldemo.infrastructure.camel.kamelet

import com.github.alextremp.cameldemo.infrastructure.camel.jackson.JsonMarshaler
import org.apache.camel.builder.RouteBuilder
import org.springframework.stereotype.Component

@Component
class KafkaBusKamelet(
  private val jsonMarshaler: JsonMarshaler
): RouteBuilder() {

  override fun configure() {
    routeTemplate("kafka-bus")
      .templateParameter("topic")
      .from("kamelet:source")
      .process(jsonMarshaler)
      .log("Sending|{{topic}}|\${body}")
      .to("kafka:{{topic}}")
  }
}