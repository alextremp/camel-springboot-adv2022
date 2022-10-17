package com.github.alextremp.cameldemo.infrastructure.camel.aggregator

import org.apache.camel.Exchange

interface ListAggregationRepository {

  fun addNewExchange(correlationIdKey: String, exchange: Exchange)

  fun isComplete(correlationIdKey: String, completionSizeKey: String, exchange: Exchange): Boolean

  fun buildAggregate(correlationIdKey: String, exchange: Exchange)
}