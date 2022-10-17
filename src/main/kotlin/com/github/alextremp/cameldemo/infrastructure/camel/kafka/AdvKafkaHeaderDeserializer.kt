package com.github.alextremp.cameldemo.infrastructure.camel.kafka

import org.apache.camel.component.kafka.serde.KafkaHeaderDeserializer
import org.springframework.stereotype.Component

@Component
class AdvKafkaHeaderDeserializer: KafkaHeaderDeserializer {

  override fun deserialize(key: String, value: ByteArray): Any = String(value)
}