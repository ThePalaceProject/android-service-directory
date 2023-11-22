package org.thepalaceproject.servicedirectory.boot

import org.thepalaceproject.servicedirectory.main.ServiceDirectoryWritableType

/**
 * A function used to asynchronously start up a service directory.
 */

fun interface ServiceBootLoaderType {

  /**
   * Create a new service directory. Implementations must call `progress` periodically
   * to submit progress updates.
   *
   * @param progress The progress receiver
   * @return A service directory, fully constructed
   */

  fun execute(progress: ServiceBootProgressType): ServiceDirectoryWritableType
}
