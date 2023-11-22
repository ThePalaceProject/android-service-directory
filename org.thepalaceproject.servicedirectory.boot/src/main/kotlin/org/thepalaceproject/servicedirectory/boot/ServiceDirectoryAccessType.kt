package org.thepalaceproject.servicedirectory.boot

import androidx.lifecycle.LiveData
import org.thepalaceproject.servicedirectory.main.ServiceDirectoryWritableType
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

/**
 * Access to the application service directory.
 */

interface ServiceDirectoryAccessType {

  /**
   * The service directory status events.
   */

  val status: LiveData<ServiceDirectoryStatus>

  /**
   * @return A future representing the service directory being started up
   */

  fun serviceDirectoryFuture(): CompletableFuture<ServiceDirectoryWritableType>

  /**
   * @return The service directory, waiting up to a minute for it to become available
   */

  fun serviceDirectory(): ServiceDirectoryWritableType {
    return this.serviceDirectoryFuture().get(60L, TimeUnit.SECONDS)
  }

  /**
   * Start booting the service directory using the given boot loader.
   *
   * @return The operation in progress
   */

  fun start(loader: ServiceBootLoaderType): CompletableFuture<ServiceDirectoryWritableType>
}
