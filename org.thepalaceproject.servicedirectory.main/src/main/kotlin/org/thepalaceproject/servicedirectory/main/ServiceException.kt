package org.thepalaceproject.servicedirectory.main

/**
 * A service exception.
 */

class ServiceException(
  message: String,
  cause: Throwable?
) : RuntimeException(message, cause) {

  constructor(
    message: String
  ) : this(message, null)
}
