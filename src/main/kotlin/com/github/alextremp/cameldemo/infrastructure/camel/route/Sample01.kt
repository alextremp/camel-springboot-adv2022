package com.github.alextremp.cameldemo.infrastructure.camel.route

import org.apache.camel.builder.RouteBuilder
import org.springframework.stereotype.Component

/**
 * Sample01 Hello world using a timer.
 */
@Component
class Sample01 : RouteBuilder() {

  override fun configure() {
    from("timer:sample01?period=1000&fixedRate=true&repeatCount=3")
      .routeId("sample01-timer")
      .process { it.message.body = "Hello World!" }
      .log("body|\${body}|headers|\${headers}")

    from("direct:sample01-filter")
      .routeId("sample01-filter")
      .split().jsonpath("$.data[*]")
      .log("splitted|\${body}")
      .filter(simple("\${body} contains 'yyy'")).to("direct:sample01-filtered").stop().end()
      .filter().simple("\${body} contains 'xxx'").to("direct:sample01-filtered").end()
      .to("direct:sample01-all")

    from("direct:sample01-filtered")
      .routeId("sample01-filtered")
      .log("filtered|\${body}")

    from("direct:sample01-all")
      .routeId("sample01-all")
      .log("all|\${body}")
  }
}