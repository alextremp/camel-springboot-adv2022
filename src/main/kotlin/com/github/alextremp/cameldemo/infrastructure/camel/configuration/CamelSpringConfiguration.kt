package com.github.alextremp.cameldemo.infrastructure.camel.configuration

import com.github.alextremp.cameldemo.infrastructure.camel.component.mydemo.MyDemoComponent
import org.apache.camel.CamelContext
import org.apache.camel.spring.boot.CamelContextConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CamelSpringConfiguration {

  @Bean
  fun camelContextConfiguration(
    myDemoComponent: MyDemoComponent
  ) = object : CamelContextConfiguration {

    override fun beforeApplicationStart(camelContext: CamelContext) {
      camelContext.addComponent("mydemo", myDemoComponent)
    }

    override fun afterApplicationStart(camelContext: CamelContext) {}
  }
}