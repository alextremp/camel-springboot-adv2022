package com.github.alextremp.cameldemo.infrastructure.controller

import com.github.alextremp.cameldemo.domain.shared.CollectionData
import com.github.alextremp.cameldemo.domain.sample.SampleItemAddRequest
import org.apache.camel.ProducerTemplate
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/sample02")
@RestController
class HttpSample02Controller(
  private val producerTemplate: ProducerTemplate
) {

  @PostMapping
  @ResponseStatus(HttpStatus.ACCEPTED)
  fun postSingleData(@RequestBody request: SampleItemAddRequest) {
    producerTemplate.asyncSendBody("direct:sample02-single", request)
  }

  @PostMapping("/bulk")
  @ResponseStatus(HttpStatus.ACCEPTED)
  fun postListOfData(@RequestBody request: CollectionData<SampleItemAddRequest>) {
    producerTemplate.asyncSendBody("direct:sample02-list", request)
  }
}