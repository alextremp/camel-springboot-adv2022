package com.github.alextremp.cameldemo.infrastructure.camel.aggregator

import org.apache.camel.AggregationStrategy
import org.apache.camel.Exchange
import org.apache.camel.Predicate
import org.apache.camel.Processor

class SampleItemAggregationStrategy(
  private val correlationIdKey: String,
  private val completionSizeKey: String,
  private val repository: ListAggregationRepository
) : AggregationStrategy, Predicate, Processor {

  override fun aggregate(oldExchange: Exchange?, newExchange: Exchange): Exchange {
    repository.addNewExchange(
      correlationIdKey = correlationIdKey,
      exchange = newExchange
    )
    return newExchange
  }

  override fun matches(exchange: Exchange?): Boolean {
    exchange ?: return false
    return repository.isComplete(
      correlationIdKey = correlationIdKey,
      completionSizeKey = completionSizeKey,
      exchange = exchange,
    )
  }

  override fun process(exchange: Exchange) {
    repository.buildAggregate(
      correlationIdKey = correlationIdKey,
      exchange = exchange
    )
  }
}