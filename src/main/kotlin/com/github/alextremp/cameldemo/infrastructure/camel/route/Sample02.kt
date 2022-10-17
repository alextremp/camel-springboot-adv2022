package com.github.alextremp.cameldemo.infrastructure.camel.route

import org.apache.camel.builder.RouteBuilder
import org.springframework.stereotype.Component

/**
 * Sample02 Routing process pipelines.
 */
@Component
class Sample02: RouteBuilder() {

  override fun configure() {

    from("direct:sample02-single")
      .routeId("sample02-single")
      .log("received <<\${body}>>")

    from("direct:sample02-list")
      .routeId("sample02-list")
      .log("received <<\${body}>>")
      .split().jsonpath("$.data[*]").parallelProcessing()
      .to("direct:sample02-single")
  }
}