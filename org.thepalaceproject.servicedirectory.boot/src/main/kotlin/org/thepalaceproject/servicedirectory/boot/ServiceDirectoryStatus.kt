package org.thepalaceproject.servicedirectory.boot

import org.thepalaceproject.servicedirectory.main.ServiceDirectoryWritableType

/**
 * The status of the current application service directory.
 */

sealed class ServiceDirectoryStatus {

  /**
   * The service directory is starting up.
   */

  data class Starting(
    val message: String
  ) : ServiceDirectoryStatus()

  /**
   * The service directory is started up and is available.
   */

  data class Available(
    val directory: ServiceDirectoryWritableType
  ) : ServiceDirectoryStatus()

  /**
   * The service directory failed to start up.
   */

  data class Failed(
    val message: String,
    val cause: Throwable
  ) : ServiceDirectoryStatus()
}
