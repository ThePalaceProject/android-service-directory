package org.thepalaceproject.servicedirectory.tests

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.thepalaceproject.servicedirectory.main.ServiceDirectory
import org.thepalaceproject.servicedirectory.main.ServiceException
import org.thepalaceproject.servicedirectory.main.ServiceInfo
import org.thepalaceproject.servicedirectory.main.ServiceType
import java.io.Closeable
import java.io.IOException
import java.util.List


class ServiceDirectoryTest {

  private class FakeService : ServiceType {
    override val info: ServiceInfo
      get() = ServiceInfo(
        packageGroup = "org.thepalaceproject.example",
        packageArtifact = "example",
        packageCommit = "ed71cf9ec935691ed47b8829aef36a46f38a362e",
        packageVersion = "0.0.1-SNAPSHOT",
        description = "Fake service."
      )
  }

  private class CrashClosedService : Closeable, ServiceType {
    override val info: ServiceInfo
      get() = ServiceInfo(
        packageGroup = "org.thepalaceproject.example",
        packageArtifact = "example",
        packageCommit = "ed71cf9ec935691ed47b8829aef36a46f38a362e",
        packageVersion = "0.0.1-SNAPSHOT",
        description = "Crash closed service."
      )

    @kotlin.Throws(IOException::class)
    override fun close() {
      throw IOException("Cannot close!")
    }
  }

  @BeforeEach
  fun setup() {

  }

  /**
   * Retrieving a registered service works.
   *
   * @throws Exception On errors
   */

  @Test
  @Throws(Exception::class)
  fun testRegisterGet() {
    val s = ServiceDirectory.create()

    assertThrows(ServiceException::class.java) {
      s.requireService(FakeService::class.java)
    }

    val f = FakeService()
    s.register(FakeService::class.java, f)
    assertEquals(f, s.requireService(FakeService::class.java))
    assertEquals(f, s.optionalService(FakeService::class.java))
    assertEquals(listOf(f), s.services())
    s.close()
  }

  /**
   * Retrieving registered services works.
   *
   * @throws Exception On errors
   */

  @Test
  @Throws(java.lang.Exception::class)
  fun testRegisterGetMultiple() {
    val s = ServiceDirectory.create()

    assertThrows(ServiceException::class.java) { s.requireService(FakeService::class.java) }
    val f0 = FakeService()
    val f1 = FakeService()
    val f2 = FakeService()
    s.register(FakeService::class.java, f0)
    s.register(FakeService::class.java, f1)
    s.register(FakeService::class.java, f2)
    assertEquals(
      listOf(f0, f1, f2),
      s.optionalServices(FakeService::class.java)
    )
    s.close()
  }

  /**
   * A service that crashes on closing results in an exception.
   *
   * @throws Exception On errors
   */

  @Test
  @Throws(java.lang.Exception::class)
  fun testCrashClosed() {
    val s = ServiceDirectory.create()

    val f = CrashClosedService()
    s.register(CrashClosedService::class.java, f)
    val ex: IOException = assertThrows(IOException::class.java, s::close)
    assertTrue(ex.message!!.contains("Cannot close!"))
  }

  /**
   * Deregistering a service works.
   *
   * @throws Exception On errors
   */

  @Test
  @Throws(java.lang.Exception::class)
  fun testDeregisterGet() {
    val s = ServiceDirectory.create()

    assertThrows(ServiceException::class.java) { s.requireService(FakeService::class.java) }
    val f = FakeService()
    s.register(FakeService::class.java, f)
    assertEquals(f, s.requireService(FakeService::class.java))
    assertEquals(f, s.optionalService(FakeService::class.java))
    assertEquals(List.of(f), s.services())
    s.deregister(FakeService::class.java, f)
    assertThrows(ServiceException::class.java) { s.requireService(FakeService::class.java) }
    assertEquals(listOf<ServiceType>(), s.services())
    s.close()
  }

  /**
   * Retrieving registered services works.
   *
   * @throws Exception On errors
   */

  @Test
  @Throws(java.lang.Exception::class)
  fun testDeregisterMultiple() {
    val s = ServiceDirectory.create()

    assertThrows(ServiceException::class.java) { s.requireService(FakeService::class.java) }
    val f0 = FakeService()
    val f1 = FakeService()
    val f2 = FakeService()
    s.register(FakeService::class.java, f0)
    s.register(FakeService::class.java, f1)
    s.register(FakeService::class.java, f2)
    assertEquals(
      List.of(f0, f1, f2),
      s.optionalServices(FakeService::class.java)
    )
    s.deregister(FakeService::class.java, f1)
    assertEquals(
      List.of(f0, f2),
      s.optionalServices(FakeService::class.java)
    )
    s.deregister(FakeService::class.java, f2)
    assertEquals(
      List.of(f0),
      s.optionalServices(FakeService::class.java)
    )
    s.deregister(FakeService::class.java, f0)
    assertEquals(
      listOf<ServiceType>(),
      s.optionalServices(FakeService::class.java)
    )
    s.deregister(FakeService::class.java, f0)
    s.close()
  }

  /**
   * Deregistering services works.
   *
   * @throws Exception On errors
   */
  @Test
  @Throws(java.lang.Exception::class)
  fun testDeregisterAll() {
    val s = ServiceDirectory.create()
    assertThrows(ServiceException::class.java) { s.requireService(FakeService::class.java) }
    val f0 = FakeService()
    val f1 = FakeService()
    val f2 = FakeService()
    s.register(FakeService::class.java, f0)
    s.register(FakeService::class.java, f1)
    s.register(FakeService::class.java, f2)
    assertEquals(
      List.of(f0, f1, f2),
      s.optionalServices(FakeService::class.java)
    )
    s.deregisterAll(FakeService::class.java)
    assertEquals(
      listOf<ServiceType>(),
      s.optionalServices(FakeService::class.java)
    )
    s.deregisterAll(FakeService::class.java)
    s.close()
  }
}
