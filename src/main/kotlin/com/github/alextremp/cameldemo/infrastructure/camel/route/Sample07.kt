package com.github.alextremp.cameldemo.infrastructure.camel.route

import org.apache.camel.builder.RouteBuilder
import org.springframework.stereotype.Component

@Component
class Sample07: RouteBuilder() {

  override fun configure() {

    from("timer:sample07?period=5000&fixedRate=true&repeatCount=5")
      .routeId("sample07-timer")
      .process { it.message.body = "Hello MyDemo Component!" }
      .log("body|\${body}|headers|\${headers}")
      .to("mydemo:hello")

    from("mydemo:hello")
      .routeId("sample07-mydemo-hello")
      .log("body|\${body}|headers|\${headers}")
  }


}