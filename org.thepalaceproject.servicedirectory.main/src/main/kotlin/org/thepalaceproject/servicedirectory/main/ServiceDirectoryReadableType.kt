package org.thepalaceproject.servicedirectory.main

import java.io.Closeable

/**
 * The service directory.
 */

interface ServiceDirectoryReadableType : Closeable {

  /**
   * Get an optional reference to the given service.
   *
   * @param clazz The service interface
   * @param <T>   The service type
   *
   * @return A service reference, if a service exists
   */

  fun <T : ServiceType> optionalService(clazz: Class<T>): T?

  /**
   * Get a required reference to the given service.
   *
   * @param clazz The service interface
   * @param <T>   The service type
   *
   * @return A service reference
   *
   * @throws ServiceException If no service exists of type */

  @Throws(ServiceException::class)
  fun <T : ServiceType> requireService(clazz: Class<T>): T

  /**
   * Get references to the given services.
   *
   * @param clazz The service interface
   * @param <T>   The service type
   *
   * @return A service list, if services exist
   *
   * @throws ServiceException If no required
   */

  @Throws(ServiceException::class)
  fun <T : ServiceType> optionalServices(clazz: Class<T>): List<T>

  /**
   * @return A read-only list of all the services present
   */

  fun services(): List<ServiceType>
}
