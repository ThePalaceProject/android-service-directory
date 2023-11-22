package org.thepalaceproject.servicedirectory.main

import org.slf4j.LoggerFactory
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap
import java.util.stream.Collectors

/**
 * A service directory implementation.
 */

class ServiceDirectory private constructor(
  private val serviceLock: Any,
  private val services: ConcurrentHashMap<Class<*>, MutableList<Any>>
) : ServiceDirectoryWritableType {

  private val logger =
    LoggerFactory.getLogger(ServiceDirectory::class.java)

  companion object {

    /**
     * Create a new empty service directory.
     */

    fun create(): ServiceDirectoryWritableType {
      return ServiceDirectory(
        serviceLock = Any(),
        services = ConcurrentHashMap()
      )
    }
  }

  override fun <T : ServiceType> register(
    clazz: Class<T>,
    service: T
  ) {
    this.logger.debug("Register: {} → {}", clazz, service)

    synchronized(this.serviceLock) {
      var existing: MutableList<Any>? = this.services[clazz]
      if (existing == null) {
        existing = mutableListOf()
      }

      existing.add(service)
      this.services.put(clazz as Class<*>, existing)
    }
  }

  override fun <T : ServiceType> deregister(
    clazz: Class<T>,
    service: T
  ) {
    synchronized(this.serviceLock) {
      val existing: MutableList<Any>? = this.services[clazz]
      if (existing != null) {
        existing.remove(service)
        if (existing.isEmpty()) {
          this.services.remove(clazz)
        }
      }
    }
  }

  override fun <T : ServiceType> deregisterAll(
    clazz: Class<T>
  ) {
    synchronized(this.serviceLock) { this.services.remove(clazz as Class<*>) }
  }

  @Suppress("UNCHECKED_CAST")
  override fun <T : ServiceType> optionalService(
    clazz: Class<T>
  ): T? {
    return synchronized(this.serviceLock) {
      val services = this.services[clazz]
      if (!services.isNullOrEmpty()) {
        services[0] as T
      } else {
        null
      }
    }
  }

  override fun <T : ServiceType> requireService(
    clazz: Class<T>
  ): T {
    return this.optionalService(clazz)
      ?: throw ServiceException(
        String.format("No implementations available of type %s", clazz.canonicalName)
      )
  }

  override fun <T : ServiceType> optionalServices(
    clazz: Class<T>
  ): List<T> {
    return synchronized(this.serviceLock) {
      (this.services.get(clazz) ?: listOf<T>()).map { x -> clazz.cast(x) }
    }
  }

  override fun services(): List<ServiceType> {
    return synchronized(this.serviceLock) {
      this.services.values
        .stream()
        .flatMap { x -> x.stream() }
        .map { x -> ServiceType::class.java.cast(x) }
        .collect(Collectors.toList())
    }
  }

  override fun close() {
    val allServices = this.services()
    var exception: Exception? = null
    for (service in allServices) {
      if (service is AutoCloseable) {
        try {
          this.logger.debug("Close: {}", service)
          service.close()
        } catch (e: Exception) {
          if (exception == null) {
            exception = e
          } else {
            exception.addSuppressed(e)
          }
        }
      }
    }

    if (exception != null) {
      throw IOException(exception)
    }
  }
}
