package com.github.alextremp.cameldemo.infrastructure.camel.route

import com.github.alextremp.cameldemo.domain.sample.SampleItem
import com.github.alextremp.cameldemo.domain.sample.SampleItemAddRequest
import com.github.alextremp.cameldemo.infrastructure.camel.aggregator.SampleItemAggregationStrategyFactory
import com.github.alextremp.cameldemo.infrastructure.camel.kafka.AdvKafkaHeaders
import org.apache.camel.builder.RouteBuilder
import org.springframework.stereotype.Component
import java.util.UUID


/**
 * Sample05 Aggregating messages.
 */
@Component
class Sample05(
  private val sampleItemAggregationStrategyFactory: SampleItemAggregationStrategyFactory
) : RouteBuilder() {

  override fun configure() {

    val sampleItemCorrelationIdKey = AdvKafkaHeaders.create("sample-item-correlation-id")
    val sampleItemCountKey = AdvKafkaHeaders.create("sample-item-count")
    val sampleItemAggregationStrategy =
      sampleItemAggregationStrategyFactory.create(
        correlationIdKey = sampleItemCorrelationIdKey,
        completionSizeKey = sampleItemCountKey
      )

    from("direct:sample05-inbox")
      .routeId("sample05-inbox")
      .log("Received|\${body}")
      .kamelet("kafka-bus?topic=priv.cameldemo.sample05.inbox")

    from("kafka:priv.cameldemo.sample05.inbox")
      .routeId("sample05-priv.cameldemo.sample05.inbox")
      .setHeader(sampleItemCorrelationIdKey) { UUID.randomUUID().toString() }
      .setHeader(sampleItemCountKey).jsonpath("$.data.length()")
      .split().jsonpath("$.data[*]").parallelProcessing()
      .kamelet("kafka-bus?topic=priv.cameldemo.sample05.item")

    from("kafka:priv.cameldemo.sample05.item")
      .routeId("sample05-priv.cameldemo.sample05.item")
      .log("Received Request|\${body}")
      .to("seda:sample05-item-queue")

    from("seda:sample05-item-queue?concurrentConsumers=5")
      .routeId("sample05-item-queue")
      .log("Add Request|\${body}")
      .convertBodyTo(SampleItemAddRequest::class.java)
      .convertBodyTo(SampleItem::class.java)
      .process { Thread.sleep(5000) }
      .log("Sending Item|\${body}")
      .to("seda:sample05-item-aggregator")

    from("seda:sample05-item-aggregator?concurrentConsumers=2")
      .routeId("sample05-item-aggregator")
      .log("Aggregating|\${body}")
      .aggregate(sampleItemAggregationStrategy).constant(true)
      .process(sampleItemAggregationStrategy)
      .log("Sending Item|\${body}")
      .kamelet("kafka-bus?topic=priv.cameldemo.sample05.outbox")


    from("kafka:priv.cameldemo.sample05.outbox")
      .routeId("sample05-priv.cameldemo.sample05.outbox")
      .log("Outbox|\${body}")
  }
}