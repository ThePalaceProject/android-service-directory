package org.thepalaceproject.servicedirectory.boot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.slf4j.LoggerFactory
import org.thepalaceproject.servicedirectory.main.ServiceDirectoryWritableType
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory

/**
 * Access to the global service directory.
 */

object Services : ServiceDirectoryAccessType {

  private val logger =
    LoggerFactory.getLogger(Services::class.java)

  private val bootExecutor =
    Executors.newSingleThreadExecutor(ThreadFactory { r ->
      val thread = Thread(r)
      thread.priority = Thread.MAX_PRIORITY
      thread.name = "org.thepalaceproject.servicedirectory.boot"
      return@ThreadFactory thread
    })

  private val statusMutable: MutableLiveData<ServiceDirectoryStatus> =
    MutableLiveData<ServiceDirectoryStatus>(ServiceDirectoryStatus.Starting("…"))

  @Volatile
  private var servicesFuture: CompletableFuture<ServiceDirectoryWritableType> =
    CompletableFuture()

  override val status: LiveData<ServiceDirectoryStatus>
    get() = this.statusMutable

  override fun serviceDirectoryFuture(): CompletableFuture<ServiceDirectoryWritableType> {
    return this.servicesFuture
  }

  override fun start(loader: ServiceBootLoaderType): CompletableFuture<ServiceDirectoryWritableType> {
    this.logger.debug("Boot: Starting...")
    this.servicesFuture = CompletableFuture()
    this.statusMutable.postValue(ServiceDirectoryStatus.Starting("…"))
    this.bootExecutor.execute {
      try {
        this.logger.debug("Boot: Starting boot loader...")
        this.servicesFuture.complete(
          loader.execute { message ->
            this.logger.debug("Boot: {}", message)
            this.statusMutable.postValue(ServiceDirectoryStatus.Starting(message))
          }
        )
        this.logger.debug("Boot: Completed!")
        this.statusMutable.postValue(ServiceDirectoryStatus.Available(this.servicesFuture.get()))
      } catch (e: Throwable) {
        this.logger.error("Boot: Failed: ", e)
        this.servicesFuture.completeExceptionally(e)
        this.statusMutable.postValue(ServiceDirectoryStatus.Failed(e.message ?: "Boot failed!", e))
      }
    }
    return this.servicesFuture
  }
}
