package org.thepalaceproject.servicedirectory.main

/**
 * The type of (writable) service directories.
 */

interface ServiceDirectoryWritableType : ServiceDirectoryReadableType {

  /**
   * Register the given service.
   *
   * @param clazz   The service interface
   * @param service The service
   * @param <T>     The type of service
   */

  fun <T : ServiceType> register(
    clazz: Class<T>,
    service: T
  )

  /**
   * Deregister the given service.
   *
   * @param clazz   The service interface
   * @param service The service
   * @param <T>     The type of service
   */

  fun <T : ServiceType> deregister(
    clazz: Class<T>,
    service: T
  )

  /**
   * Deregister all service instance of the given type.
   *
   * @param clazz The service interface
   * @param <T>   The type of service
   */

  fun <T : ServiceType> deregisterAll(clazz: Class<T>)
}
