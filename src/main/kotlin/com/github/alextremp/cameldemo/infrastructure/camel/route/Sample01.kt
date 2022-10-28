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

    from("direct:sample01-single")
      .routeId("sample01-single")
      .log("received <<\${body}>>")

    from("direct:sample01-list")
      .routeId("sample01-list")
      .log("received <<\${body}>>")
      .split().jsonpath("$.data[*]")
      .to("direct:sample01-single")
  }
}