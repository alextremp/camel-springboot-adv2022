package com.github.alextremp.cameldemo.infrastructure.camel.route

import org.apache.camel.builder.RouteBuilder
import org.springframework.stereotype.Component

/**
 * Sample02 Routing process pipelines.
 */
@Component
class Sample02: RouteBuilder() {

  override fun configure() {

    from("direct:sample02-filter")
      .routeId("sample02-filter")
      .split().jsonpath("$.data[*]").parallelProcessing()
      .log("splitted|\${body}")
      .filter(simple("\${body} contains 'yyy'")).to("direct:sample02-filtered").stop().end()
      .filter().simple("\${body} contains 'xxx'").to("direct:sample02-filtered").end()
      .to("direct:sample02-all")

    from("direct:sample02-filtered")
      .routeId("sample02-filtered")
      .log("filtered|\${body}")

    from("direct:sample02-all")
      .routeId("sample02-all")
      .log("all|\${body}")
  }
}