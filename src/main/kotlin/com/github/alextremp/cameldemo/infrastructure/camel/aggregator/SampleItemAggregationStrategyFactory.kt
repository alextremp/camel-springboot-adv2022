package com.github.alextremp.cameldemo.infrastructure.camel.aggregator

import org.springframework.stereotype.Component

@Component
class SampleItemAggregationStrategyFactory(
  private val listAggregationRepository: ListAggregationRepository
) {

  fun create(
    correlationIdKey: String,
    completionSizeKey: String,
  ) = SampleItemAggregationStrategy(
    correlationIdKey = correlationIdKey,
    completionSizeKey = completionSizeKey,
    repository = listAggregationRepository
  )
}