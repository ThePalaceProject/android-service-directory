package org.thepalaceproject.servicedirectory.main

/**
 * Information about a service.
 */

data class ServiceInfo(

  /**
   * The package group (such as "org.thepalaceproject.servicedirectory").
   */

  val packageGroup: String,

  /**
   * The package artifact (such as "org.thepalaceproject.servicedirectory.main").
   */

  val packageArtifact: String,

  /**
   * The package version (such as "1.2.0-SNAPSHOT").
   */

  val packageVersion: String,

  /**
   * The package commit (such as "a310973577e55ab61882e3618d1193a1d0d3383a").
   */

  val packageCommit: String,

  /**
   * The service description (humanly-readable, "An HTTP client service.").
   */

  val description: String
)
