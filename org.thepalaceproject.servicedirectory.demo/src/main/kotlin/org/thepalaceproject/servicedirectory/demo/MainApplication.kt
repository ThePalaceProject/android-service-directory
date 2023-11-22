package org.thepalaceproject.servicedirectory.demo

import android.app.Application
import org.thepalaceproject.servicedirectory.boot.Services
import org.thepalaceproject.servicedirectory.main.ServiceDirectory

/**
 * The main activity stores the service directory, and all applications state is held inside
 * services.
 */

class MainApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    Services.start(loader = { progress ->
      val services = ServiceDirectory.create()
      progress.onServiceStart("ExampleService0")
      services.register(ExampleService0::class.java, ExampleService0())
      Thread.sleep(1_000L)
      progress.onServiceStart("ExampleService1")
      services.register(ExampleService1::class.java, ExampleService1())
      Thread.sleep(1_000L)
      progress.onServiceStart("ExampleService2")
      services.register(ExampleService2::class.java, ExampleService2())
      Thread.sleep(1_000L)
      progress.onProgressUpdate("Finished!")
      Thread.sleep(1_000L)
      return@start services
    })
  }
}
