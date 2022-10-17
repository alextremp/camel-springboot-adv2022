package com.github.alextremp.cameldemo.infrastructure.camel.kafka

object AdvKafkaHeaders {

  private const val PREFIX = "adv2022."

  fun isAdvKafkaHeader(headerName: String) = headerName.startsWith(PREFIX)

  fun create(header: String) = "$PREFIX${header.removePrefix(PREFIX)}"
}