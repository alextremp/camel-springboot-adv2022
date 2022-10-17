package com.github.alextremp.cameldemo.infrastructure.camel.jackson

import com.github.alextremp.cameldemo.domain.sample.SampleItemAddRequest
import org.springframework.stereotype.Component

@Component
class SampleAddRequestUnmarshaler : AbstractPojoUnmarshaler<SampleItemAddRequest>(
  type = SampleItemAddRequest::class.java
)