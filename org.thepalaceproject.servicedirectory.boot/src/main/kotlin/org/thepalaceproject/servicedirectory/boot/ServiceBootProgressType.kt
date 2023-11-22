package org.thepalaceproject.servicedirectory.boot

/**
 * A function used to receive progress updates.
 */

fun interface ServiceBootProgressType {

  /**
   * A progress update was received.
   */

  fun onProgressUpdate(message: String)

  /**
   * A particular service was started.
   */

  fun onServiceStart(name: String) {
    this.onProgressUpdate(String.format("Starting %s...", name))
  }
}
