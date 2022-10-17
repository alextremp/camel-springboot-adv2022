package com.github.alextremp.cameldemo.domain.shared

data class CollectionData<T>(
  val data: List<T> = emptyList()
)