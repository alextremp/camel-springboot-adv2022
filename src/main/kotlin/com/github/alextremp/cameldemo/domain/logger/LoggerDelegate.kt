package com.github.alextremp.cameldemo.domain.logger

import java.util.logging.Logger
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class LoggerDelegate : ReadOnlyProperty<Any?, Logger> {
  companion object {
    private fun <T> createLogger(clazz: Class<T>): Logger = Logger.getLogger(clazz.name)
  }

  private var logger: Logger? = null
  override operator fun getValue(thisRef: Any?, property: KProperty<*>): Logger {
    if (logger == null) {
      logger = createLogger(thisRef!!.javaClass)
    }
    return logger!!
  }
}
