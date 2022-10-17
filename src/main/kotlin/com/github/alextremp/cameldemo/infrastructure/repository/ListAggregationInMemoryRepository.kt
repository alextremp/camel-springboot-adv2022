package com.github.alextremp.cameldemo.infrastructure.repository

import com.github.alextremp.cameldemo.infrastructure.camel.aggregator.ListAggregationRepository
import org.apache.camel.Exchange
import org.springframework.stereotype.Repository

@Repository
class ListAggregationInMemoryRepository(
  private val messages: MutableMap<String, MutableList<Any>> = mutableMapOf()
) : ListAggregationRepository {

  @Synchronized
  override fun addNewExchange(correlationIdKey: String, exchange: Exchange) {
    val correlationId = correlationId(correlationIdKey, exchange)
    if (!messages.containsKey(correlationId)) {
      messages[correlationId] = mutableListOf()
    }
    messages[correlationId]!!.add(exchange.message.body)
  }

  override fun isComplete(correlationIdKey: String, completionSizeKey: String, exchange: Exchange): Boolean {
    val correlationId = correlationId(correlationIdKey, exchange)
    val aggregatedSize = messages[correlationId]?.size ?: return false
    val completionSize = completionSize(completionSizeKey, exchange)
    return aggregatedSize == completionSize
  }

  override fun buildAggregate(correlationIdKey: String, exchange: Exchange) {
    val correlationId = correlationId(correlationIdKey, exchange)
    val aggregated = messages[correlationId] ?: return
    exchange.message.body = aggregated.toList()
  }

  private fun correlationId(correlationIdKey: String, exchange: Exchange) =
    exchange.message.headers[correlationIdKey]?.let { "$it" } ?: ""

  private fun completionSize(completionSizeKey: String, exchange: Exchange) =
    exchange.message.headers[completionSizeKey]?.let { "$it" }?.toInt() ?: -1
}