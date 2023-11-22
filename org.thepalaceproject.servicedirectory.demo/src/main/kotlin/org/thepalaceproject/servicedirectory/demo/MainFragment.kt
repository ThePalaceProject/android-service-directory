package org.thepalaceproject.servicedirectory.demo

import androidx.fragment.app.Fragment
import org.thepalaceproject.servicedirectory.boot.Services

/**
 * A stateless fragment that fetches services on startup.
 */

class MainFragment : Fragment(R.layout.main_fragment) {

  private var service2: ExampleService2? = null
  private lateinit var service1: ExampleService1
  private lateinit var service0: ExampleService0

  override fun onStart() {
    super.onStart()

    /*
     * Note that `serviceDirectory()` is a call that blocks the UI thread. However, the call
     * cannot block here if the `MainActivity` is doing its job correctly and only adding
     * fragments when the service directory is started.
     */

    val services = Services.serviceDirectory()
    this.service0 =
      services.requireService(ExampleService0::class.java)
    this.service1 =
      services.requireService(ExampleService1::class.java)
    this.service2 =
      services.optionalService(ExampleService2::class.java)
  }
}
