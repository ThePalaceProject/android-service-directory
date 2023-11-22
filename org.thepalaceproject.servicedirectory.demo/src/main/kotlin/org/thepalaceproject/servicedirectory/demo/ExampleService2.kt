package org.thepalaceproject.servicedirectory.demo

import org.thepalaceproject.servicedirectory.main.ServiceInfo
import org.thepalaceproject.servicedirectory.main.ServiceType

/**
 * An example service.
 */

class ExampleService2 : ServiceType {
  override val info: ServiceInfo
    get() = ServiceInfo(
      BuildConfig.GROUP_NAME,
      BuildConfig.ARTIFACT_NAME,
      BuildConfig.VERSION_NAME,
      BuildConfig.COMMIT_NAME,
      "Example Service 2"
    )
}
