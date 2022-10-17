package com.github.alextremp.cameldemo.domain.sample

import java.util.UUID

data class SampleItem(
  val id: String = UUID.randomUUID().toString(),
  val name: String
)