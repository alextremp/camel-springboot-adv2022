package com.github.alextremp.cameldemo.infrastructure.camel.route

import org.apache.camel.builder.RouteBuilder
import org.springframework.stereotype.Component
import java.util.logging.Logger

@Component
class Sample06 : RouteBuilder(){
  val logger: Logger = Logger.getLogger(Sample06::class.java.name)

  override fun configure() {
    val dlc = deadLetterChannel("seda:sample06-error")
      .maximumRedeliveries(3)
      .redeliveryDelay(1000L)
      .onRedelivery { exchange ->
        logger.info { "Redelivering|${exchange.message.body}|${exchange.message.headers}" }
      }
      .useOriginalMessage()

    errorHandler(dlc)

    from("direct:sample06-inbox")
      .routeId("sample06-inbox")
      .log("Received|\${body}|\${headers}")
      .to("seda:sample06-01")

    from("seda:sample06-01")
      .routeId("sample06-01")
      .log("Received|\${body}|\${headers}")
      .process { it.message.body = it.message.getBody(String::class.java) + "|01" }
      .to("seda:sample06-02")

    from("seda:sample06-02")
      .routeId("sample06-02")
      .log("Received|\${body}|\${headers}")
      .process { it.message.body = it.message.getBody(String::class.java) + "|02.1" }
      .process {
        it.message.body = it.message.getBody(String::class.java) + "|02.2"
        logger.info { "Throwing exception|${it.message.body}|${it.message.headers}" }
        throw RuntimeException("Something went wrong")
      }

    from("seda:sample06-error")
      .routeId("sample06-error")
      .process { it.message.body = it.message.getBody(String::class.java) + "|error" }
      .process { logger.info { "received|${it.message.body}|${it.message.headers}|${it.allProperties}" } }
  }
}