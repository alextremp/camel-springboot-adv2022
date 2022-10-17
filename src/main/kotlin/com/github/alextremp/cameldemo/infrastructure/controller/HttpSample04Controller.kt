package com.github.alextremp.cameldemo.infrastructure.controller

import org.apache.camel.ProducerTemplate
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/sample04")
@RestController
class HttpSample04Controller(
  private val producerTemplate: ProducerTemplate
) {

  @PostMapping("/bulk")
  @ResponseStatus(HttpStatus.ACCEPTED)
  fun postListOfData(@RequestBody request: String) {
    producerTemplate.asyncSendBody("direct:sample04-inbox", request)
  }
}