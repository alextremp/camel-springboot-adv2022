package com.github.alextremp.cameldemo.infrastructure.controller

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

  @PostMapping("/filter")
  @ResponseStatus(HttpStatus.ACCEPTED)
  fun postSingleData(@RequestBody request: String) {
    producerTemplate.asyncSendBody("direct:sample02-filter", request)
  }
}